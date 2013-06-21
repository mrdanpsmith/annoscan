Annoscan
========
Simple reflective annotation scanner for Java applications.

Usage
-----
Instantiate an AnnotationScanner and then scan an object for annotated properties:

```
AnnotationScanner scanner = new AnnotationScannerImpl(new GetterLocatorImpl());
properties = simpleAnnotationScanner.getAnnotatedProperties(TestClass.class,TestAnnotation.class);
```

After that, you can iterate through all of the properties and get the annotation or the value of the field.

```
for (AnnotatedProperty<TestAnnotation> property: properties) {
	TestAnnotation annotation = property.getAnnotation();
	Object value = property.getPropertyAccessor().getValue();
}
```
</code>
