package com.siirush.annoscan.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface GetterLocator {
	/**
	 * @param field Field object for which to find the getter method.
	 * @return The object's getter method for the field.
	 */
	Method findGetterMethod(Field field);
}