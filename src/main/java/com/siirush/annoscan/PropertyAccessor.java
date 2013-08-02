package com.siirush.annoscan;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.siirush.annoscan.exception.PropertyAccessException;

public class PropertyAccessor {
	public enum PropertyAccessType {
		METHOD, FIELD
	}
	final Method method;
	final Field field;
	final PropertyAccessType propertyAccessType;
	public PropertyAccessor(Method method) {
		this.propertyAccessType = PropertyAccessType.METHOD;
		this.method = method;
		this.field = null;
	}
	public PropertyAccessor(Field field) {
		this.propertyAccessType = PropertyAccessType.FIELD;
		this.field = field;
		this.method = null;
	}
	/**
	 * @param target The object from which to get the value.
	 * @return The underlying object's value for a field.
	 */
	public Object getValue(Object target) {
		Exception thrown;
		try {
			return getValueFromUnderlyingObject(target);
		} catch (IllegalArgumentException e) {
			thrown = e;
		} catch (IllegalAccessException e) {
			thrown = e;
		} catch (InvocationTargetException e) {
			thrown = e;
		}
		throw new PropertyAccessException(thrown);
	}
	/**
	 * @return The method that will be called to access the property value.
	 */
	public Method getMethod() {
		return method;
	}
	/**
	 * @return The field that will be called to access the property value.
	 */
	public Field getField() {
		return field;
	}
	/**
	 * @return The type of access for the property value.
	 */
	public PropertyAccessType getPropertyAccessType() {
		return propertyAccessType;
	}
	private Object getValueFromUnderlyingObject(Object target) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Object value;
		if (propertyAccessType.equals(PropertyAccessType.FIELD)) {
			value = field.get(target);
		} else {
			value = method.invoke(target);
		}
		return value;
	}
	@Override
	public int hashCode() {
		int hashCode = propertyAccessType.hashCode();
		if (propertyAccessType.equals(PropertyAccessType.FIELD)) {
			hashCode += field.hashCode();
		} else {
			hashCode += method.hashCode();
		}
		return hashCode;
	}
	@Override
	public boolean equals(Object obj) {
		PropertyAccessor other;
		boolean equals;
		if (obj instanceof PropertyAccessor) {
			other = (PropertyAccessor)obj;
			equals = equals(other);
		} else {
			equals = false;
		}
		return equals;
	}
	private boolean equals(PropertyAccessor other) {
		boolean equals;
		if (!propertyAccessType.equals(other.propertyAccessType)) {
			equals = false;
		} else if (propertyAccessType.equals(PropertyAccessType.FIELD)) {
			equals = field.equals(other.field);
		} else {
			equals = method.equals(other.method);
		}
		return equals;
	}
}
