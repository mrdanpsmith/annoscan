package com.siirush.annoscan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;

import com.siirush.annoscan.AnnotatedProperty;
import com.siirush.annoscan.AnnoScanImpl;
import com.siirush.annoscan.PropertyAccessor;
import com.siirush.annoscan.reflection.GetterLocatorImpl;

public class AnnoScanImplTest {
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.FIELD})	
	@interface TestAnnotation {
		int order() default Integer.MAX_VALUE;
	}
	
	class TestAnnotationComparator implements Comparator<AnnotatedProperty<TestAnnotation>> {
		public int compare(AnnotatedProperty<TestAnnotation> a, AnnotatedProperty<TestAnnotation> b) {
			return Integer.valueOf(a.getAnnotation().order()).compareTo(b.getAnnotation().order()); 
		}		
	}
	
	class TestClass {
		@TestAnnotation
		public String field = "publicField";
		
		@TestAnnotation
		public String getMethodWithoutField() {
			return "noField";
		}
		
		@TestAnnotation
		private String privateFieldWithGetter;
		
		public String getPrivateFieldWithGetter() {
			return privateFieldWithGetter;
		}
	}
	
	class TestClassWithSort {
		@TestAnnotation(order=2)
		public String secondProperty;
		
		@TestAnnotation(order=3)
		public String thirdProperty;
		
		@TestAnnotation(order=1)
		public String firstProperty;
	}
	
	TestAnnotation fieldAnnotation, getMethodWithoutFieldAnnotation, privateFieldWithGetterAnnotation;
	
	Field publicField, privateFieldWithGetter;
	
	Method methodWithoutField, privateFieldGetter;
	
	Set<AnnotatedProperty<TestAnnotation>> properties;
	SortedSet<AnnotatedProperty<TestAnnotation>> sortedProperties;
	
	AnnoScanImpl annoScan;
	
	@Before
	public void setUp() throws SecurityException, NoSuchFieldException, NoSuchMethodException {
		annoScan = new AnnoScanImpl(new GetterLocatorImpl());
		properties = annoScan.getAnnotatedProperties(TestClass.class,TestAnnotation.class);
		sortedProperties = annoScan.getAnnotatedPropertiesSorted(TestClassWithSort.class, TestAnnotation.class, new TestAnnotationComparator());
		
		publicField = TestClass.class.getField("field");
		methodWithoutField = TestClass.class.getMethod("getMethodWithoutField",new Class<?>[0]);
		privateFieldGetter = TestClass.class.getMethod("getPrivateFieldWithGetter",new Class<?>[0]);
		privateFieldWithGetter = TestClass.class.getDeclaredField("privateFieldWithGetter");
		
		fieldAnnotation = publicField.getAnnotation(TestAnnotation.class);
		getMethodWithoutFieldAnnotation = methodWithoutField.getAnnotation(TestAnnotation.class);
		privateFieldWithGetterAnnotation = privateFieldWithGetter.getAnnotation(TestAnnotation.class);
	}
	
	@Test
	public void testGetAnnotatedPropertiesRetrievesAnnotations() {
		Set<TestAnnotation> annotations = new HashSet<TestAnnotation>();
		for (AnnotatedProperty<TestAnnotation> property: properties) {
			annotations.add(property.getAnnotation());
		}
		assertTrue(annotations.contains(fieldAnnotation));
		assertTrue(annotations.contains(getMethodWithoutFieldAnnotation));
		assertTrue(annotations.contains(privateFieldWithGetterAnnotation));
	}
	
	@Test
	public void testGetAnnotatedPropertiesSortedSortsProperties() {
		int i = 1;
		for (AnnotatedProperty<TestAnnotation> property: sortedProperties) {
			assertEquals(i++,property.getAnnotation().order());
		}
	}
	
	@Test
	public void testGetAnnotatedPropertiesRetrievesPropertyAccessors() {
		Set<PropertyAccessor> accessors = new HashSet<PropertyAccessor>();
		for (AnnotatedProperty<TestAnnotation> property: properties) {
			accessors.add(property.getPropertyAccessor());
		}
		assertTrue(accessors.contains(new PropertyAccessor(publicField)));
		assertTrue(accessors.contains(new PropertyAccessor(privateFieldGetter)));
		assertTrue(accessors.contains(new PropertyAccessor(methodWithoutField)));
	}
}
