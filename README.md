Annoscan
========
Reflective annotation scanner for Java.

Why another annotation scanner?
-------------------------------
I found a lot of annotation scanners for Java, but I did not find an easy to use generic version to do a very common task: take a collection of plain Java objects and scan for annotated getters / setters and properties.

As part of my development efforts on another project, I decided to write a reusable annotation scanner.

The other project was not used and I found myself needing this code in other areas; so, I decided to publish it.

Usage
-----
Instantiate an AnnotationScanner and then scan an object for annotated properties:

```Java
AnnotationScanner scanner = new AnnotationScannerImpl(new GetterLocatorImpl());
properties = simpleAnnotationScanner.getAnnotatedProperties(TestClass.class,TestAnnotation.class);
```

After that, you can iterate through all of the properties and get the annotation or the value of the field.

```Java
for (AnnotatedProperty<TestAnnotation> property: properties) {
	TestAnnotation annotation = property.getAnnotation();
	Object value = property.getPropertyAccessor().getValue();
}
```

Goals
-----

My main goal for this project is to get it submitted to maven central.

There are a lot of requirements that go into that submission, but being able to use this on Maven built Java projects would be great.
