package com.siirush.annoscan.annotation;

import java.lang.annotation.Annotation;

public class AnnotatedProperty<T extends Annotation> {
	private final T annotation;
	private final PropertyAccessor propertyAccessor;
	public AnnotatedProperty(T annotation, PropertyAccessor accessor) {
		this.annotation = annotation;
		this.propertyAccessor = accessor;
	}
	public T getAnnotation() {
		return annotation;
	}
	public PropertyAccessor getPropertyAccessor() {
		return propertyAccessor;
	}
	@Override
	public int hashCode() {
		return super.hashCode() + annotation.hashCode() + propertyAccessor.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		boolean equals;
		if (obj instanceof AnnotatedProperty) {
			@SuppressWarnings("rawtypes")
			AnnotatedProperty other = (AnnotatedProperty)obj;
			equals = equals(other);
		} else {
			equals = false;
		}
		return equals;
	}
	private boolean equals(@SuppressWarnings("rawtypes") AnnotatedProperty other) {
		return annotation.equals(other.annotation) && propertyAccessor.equals(other.propertyAccessor);
	}
}
