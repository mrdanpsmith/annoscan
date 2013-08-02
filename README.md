Annoscan
========
Reflective annotation scanner for Java.

Why another annotation scanner?
-------------------------------
After scouring the web a few times, I could not find an easy way to do a simple task: take a class and scan for annotated properties.

As part of my development efforts on another project, I wrote such an annotation scanner.

I often found myself needing to do this same task; so, I decided to publish the code to make it accessible to others.

Installation
-----
Annoscan is in maven central.  Simply add the following dependency:

```xml
<dependency>
    <groupId>com.siirush</groupId>
    <artifactId>annoscan</artifactId>
    <version>0.1.5</version>
</dependency>
```

Configuration
-----
AnnoScan can be configured using a variety of methods.

It relies on constructor injection and is annotated in compliance with JSR-330 containers (Guice, Spring 3, etc).

In addition, it can be handwired manually or obtained using the provider singleton.

Handwired:
```Java
AnnoScan annoScan = new AnnoScanImpl(new GetterLocatorImpl());
```

Provider:
```Java
AnnoScan annoScan = AnnoScanProvider.instance.get();
```

Usage
-----
To scan a class for annotated properties, simply call one of the methods with your class and annotation class.

In unspecified order:
```Java
properties = annoScan.getAnnotatedProperties(TestClass.class,TestAnnotation.class);
```

In sorted order:
```Java
sortedProperties = annoScan.getAnnotatedPropertiesSorted(TestClassWithSort.class, TestAnnotation.class, new TestAnnotationComparator());
```

Then iterate through all of the properties and get the annotation or the value of the field:
```Java
for (AnnotatedProperty<TestAnnotation> property: properties) {
	TestAnnotation annotation = property.getAnnotation();
	Object fieldValue = property.getPropertyAccessor().getValue(annotatedObject);
}
```

Additional Notes
-----
The default AnnoScan and GetterLocator implementations are stateless and can be instantiated prototypically with minimal overhead, or as singletons.

The default provider instance get method call demonstrated above returns a singleton.
