package com.annoscan.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.annoscan.reflection.GetterLocator;

@Singleton
public class AnnotationScannerImpl implements AnnotationScanner {
	private final GetterLocator getterLocator;
	
	@Inject
	public AnnotationScannerImpl(GetterLocator getterLocatorImpl) {
		this.getterLocator = getterLocatorImpl;
	}
	
	public <T extends Annotation> Set<AnnotatedProperty<T>> getAnnotatedProperties(Class<?> classToScan, Class<T> annotationToFind) {
		Set<AnnotatedProperty<T>> properties = new HashSet<AnnotatedProperty<T>>();
		populateAnnotatedProperties(classToScan, annotationToFind, properties);
		return properties;
	}
	
	public <T extends Annotation> SortedSet<AnnotatedProperty<T>> getAnnotatedPropertiesSorted(Class<?> classToScan, Class<T> annotationToFind, Comparator<AnnotatedProperty<T>> comparator) {
		SortedSet<AnnotatedProperty<T>> properties = new TreeSet<AnnotatedProperty<T>>(comparator);
		populateAnnotatedProperties(classToScan, annotationToFind, properties);
		return properties;
	}
	
	private <T extends Annotation> void populateAnnotatedProperties(Class<?> classToScan, Class<T> annotationToFind, Set<AnnotatedProperty<T>> properties) {
		properties.addAll(findAnnotatedFields(classToScan, annotationToFind));
		properties.addAll(findAnnotatedMethods(classToScan, annotationToFind));
	}
	
	private <T extends Annotation> Set<AnnotatedProperty<T>> findAnnotatedFields(Class<?> classToScan, Class<T> annotationToFind) {
		Set<AnnotatedProperty<T>> annotatedFields = new HashSet<AnnotatedProperty<T>>();
		Field[] fields = classToScan.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.isAnnotationPresent(annotationToFind)) {
				T annotation = field.getAnnotation(annotationToFind);
				PropertyAccessor propertyAccessor = createPropertyAccessor(field);
				annotatedFields.add(new AnnotatedProperty<T>(annotation,propertyAccessor));
			}
		}
		return annotatedFields;
	}
	
	public PropertyAccessor createPropertyAccessor(Field field) {
		PropertyAccessor propertyAccessor;
		if (Modifier.isPublic(field.getModifiers())) {
			propertyAccessor = new PropertyAccessor(field);
		} else {
			propertyAccessor = createPropertyAccessorForGetter(field);
		}
		return propertyAccessor;
	}
	
	private PropertyAccessor createPropertyAccessorForGetter(Field field) {
		PropertyAccessor propertyAccessor;
		Method method = getterLocator.findGetterMethod(field);
		if (method == null) {
			propertyAccessor = null;
		} else {
			propertyAccessor = new PropertyAccessor(method);
		}
		return propertyAccessor;
	}
	
	private <T extends Annotation> Set<AnnotatedProperty<T>> findAnnotatedMethods(Class<?> classToScan, Class<T> annotationToFind) {
		Set<AnnotatedProperty<T>> annotatedMethods = new HashSet<AnnotatedProperty<T>>();
		Method[] methods = classToScan.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.isAnnotationPresent(annotationToFind)) {
				T annotation = method.getAnnotation(annotationToFind);
				PropertyAccessor propertyAccessor = new PropertyAccessor(method);
				annotatedMethods.add(new AnnotatedProperty<T>(annotation,propertyAccessor));
			}
		}
		return annotatedMethods;
	}
}
