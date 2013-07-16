package com.siirush.annoscan.annotation;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;

public interface AnnotationScanner {
	/**
	 * @param classToScan The class to scan for annotated properties.
	 * @param annotationToFind The annotation class for which to scan.
	 * @return An unordered set of properties found to have the annotation.
	 */
	<T extends Annotation> Set<AnnotatedProperty<T>> getAnnotatedProperties(
			Class<?> classToScan, Class<T> annotationToFind);

	/**
	 * @param classToScan The class to scan for annotated properties.
	 * @param annotationToFind The annotation class for which to scan.
	 * @param comparator The comparator to use in order to sort the annotated properties.
	 * @return An ordered set of properties found to have the annotation.
	 */
	<T extends Annotation> SortedSet<AnnotatedProperty<T>> getAnnotatedPropertiesSorted(
			Class<?> classToScan, Class<T> annotationToFind,
			Comparator<AnnotatedProperty<T>> comparator);

}