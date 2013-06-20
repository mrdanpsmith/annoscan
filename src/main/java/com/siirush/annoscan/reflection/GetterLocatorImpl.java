package com.siirush.annoscan.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GetterLocatorImpl implements GetterLocator {
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

	public String determineGetterMethodName(Field field) {
		String getterName;
		if (field.getClass().equals(boolean.class)) {
			getterName = determineBooleanGetterName(field.getName());
		} else {
			getterName = determineNonBooleanGetterName(field.getName());
		}
		return getterName;
	}

	private String determineNonBooleanGetterName(String fieldName) {
		return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}

	private String determineBooleanGetterName(String fieldName) {
		return "is" + fieldName + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}
}
