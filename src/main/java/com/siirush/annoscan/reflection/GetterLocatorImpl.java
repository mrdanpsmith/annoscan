package com.siirush.annoscan.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Singleton;

@Singleton
public class GetterLocatorImpl implements GetterLocator {
	private static final String BOOLEAN_GETTER_PREFIX = "is";
	private static final String STANDARD_GETTER_PREFIX = "get";
	
	public Method findGetterMethod(Field field) {
		String getterName = determineGetterMethodName(field);
		return getMethod(field.getDeclaringClass(),getterName);
	}

	private Method getMethod(Class<?> declaringClass, String methodName) {
		Exception thrown;
		try {
			return declaringClass.getMethod(methodName, new Class<?>[0]);
		} catch (SecurityException e) {
			thrown = e;
		} catch (NoSuchMethodException e) {
			thrown = e;
		}
		throw new RuntimeException(thrown);
	}

	private String determineGetterMethodName(Field field) {
		String prefix = (field.getClass().equals(boolean.class)) ? BOOLEAN_GETTER_PREFIX : STANDARD_GETTER_PREFIX;
		return buildGetterMethodName(prefix,field.getName());
	}

	private String buildGetterMethodName(String prefix, String fieldName) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(fieldName.substring(0,1).toUpperCase());
		sb.append(fieldName.substring(1));
		return sb.toString();
	}
}
