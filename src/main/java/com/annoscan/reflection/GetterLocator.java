package com.annoscan.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface GetterLocator {
	Method findGetterMethod(Field field);
	String determineGetterMethodName(Field field);
}