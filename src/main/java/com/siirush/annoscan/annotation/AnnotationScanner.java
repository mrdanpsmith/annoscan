package com.siirush.annoscan.annotation;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;

public interface AnnotationScanner {

	<T extends Annotation> Set<AnnotatedProperty<T>> getAnnotatedProperties(
			Class<?> classToScan, Class<T> annotationToFind);

	<T extends Annotation> SortedSet<AnnotatedProperty<T>> getAnnotatedPropertiesSorted(
			Class<?> classToScan, Class<T> annotationToFind,
			Comparator<AnnotatedProperty<T>> comparator);

}