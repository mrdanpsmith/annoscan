package com.siirush.annoscan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.siirush.annoscan.PropertyAccessor;
import com.siirush.annoscan.exception.PropertyAccessException;

public class PropertyAccessorTest {
	public class IncorrectAccessExample {
		private String inaccessibleField = "inaccessible";
		
		public String accessibleField = "accessible";
		
		public String getInaccessibleField() {
			return inaccessibleField;
		}
		
		@SuppressWarnings("unused")
		private String inaccessibleMethod() {
			return "inaccessibleMethod";
		}
	}
	
	IncorrectAccessExample example;
	
	@Before
	public void setUp() {
		example = new IncorrectAccessExample();
	}
	
	@Test
	public void testEqualsWithMethod() throws NoSuchMethodException, SecurityException {
		Method method = IncorrectAccessExample.class.getMethod("getInaccessibleField", new Class<?>[0]);
		assertEquals(new PropertyAccessor(method),new PropertyAccessor(method));
	}
	
	@Test
	public void testEqualsWithField() throws NoSuchFieldException, SecurityException {
		Field field = IncorrectAccessExample.class.getField("accessibleField");
		assertEquals(new PropertyAccessor(field),new PropertyAccessor(field));
	}
	
	@Test
	public void testEqualsWithNotEqualObjects() throws NoSuchFieldException, SecurityException {
		Field accessibleField = IncorrectAccessExample.class.getField("accessibleField");
		Field inaccessibleField = IncorrectAccessExample.class.getDeclaredField("inaccessibleField");
		assertNotSame(new PropertyAccessor(accessibleField), new PropertyAccessor(inaccessibleField));
	}
	
	@Test
	public void testEvaluatesCorrectly() throws NoSuchMethodException, SecurityException {
		Method validAccessor = IncorrectAccessExample.class.getMethod("getInaccessibleField", new Class<?>[0]);
		PropertyAccessor propertyAccessor = new PropertyAccessor(validAccessor);
		assertEquals(example.getInaccessibleField(),propertyAccessor.getValue(example));
	}
	
	@Test(expected=PropertyAccessException.class)
	public void testThrowsErrorUponIncorrectMethodEvaluation() throws NoSuchMethodException, SecurityException {
		Method inaccessibleMethod = IncorrectAccessExample.class.getDeclaredMethod("inaccessibleMethod", new Class<?>[0]);
		PropertyAccessor propertyAccessor = new PropertyAccessor(inaccessibleMethod);
		propertyAccessor.getValue(example);
	}
	
	@Test(expected=PropertyAccessException.class)
	public void testThrowsErrorUponIncorrectFieldEvaluation() throws NoSuchFieldException, SecurityException {
		Field inaccessibleField = IncorrectAccessExample.class.getDeclaredField("inaccessibleField");
		PropertyAccessor propertyAccessor = new PropertyAccessor(inaccessibleField);
		propertyAccessor.getValue(example);
	}
}
