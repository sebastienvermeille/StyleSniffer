# StyleSniffer Annotation Processor

## Overview

The `stylesniffer-annotation-processor` module is responsible for processing the
custom `@RegisterCaseStyle` annotation and generating a utility class that aggregates
all `CaseStyle` implementations at compile time. This enables automatic registration of custom
naming conventions, making them easily accessible at runtime without requiring manual intervention.

This module leverages Java's annotation processing capabilities, Thymeleaf for code generation, and
Flogger for logging, ensuring that the style-sniffing system remains extensible and easy to
maintain.

## Responsibilities

- **Annotation Processing**: Detects classes annotated with `@RegisterCaseStyle` and processes them
  during compilation.
- **Code Generation**: Automatically generates a `CaseStyleInjector` class that includes all
  detected `CaseStyle` implementations, making them available for runtime use.
- **Logging**: Utilizes Flogger to log any issues or events during the annotation processing.

## Key Components

### `@RegisterCaseStyle` Annotation

```java

@Retention(SOURCE)
@Target(TYPE)
public @interface RegisterCaseStyle {

}
```

The @RegisterCaseStyle annotation marks classes that should be automatically included in the
generated CaseStyleInjector class. Classes using this annotation must implement the CaseStyle
interface.

### `RegisterCaseStyleAnnotationProcessor` Class

This class processes the @RegisterCaseStyle annotation and generates the CaseStyleInjector class,
which aggregates all CaseStyle implementations for runtime use.

### Generated `CaseStyleInjector` Class

The CaseStyleInjector class is automatically generated during the build process and
includes references to all annotated CaseStyle implementations.

It is placed in the `dev.cookiecode.stylesniffer.generated` package.

## Usage Example

### Step 1: Define a Custom CaseStyle Implementation

Create a class that implements the `CaseStyle` interface and annotate it with `@RegisterCaseStyle`:

```java
import dev.cookiecode.stylesniffer.api.CaseStyle;
import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;

@RegisterCaseStyle
public class MyCustomCaseStyle implements CaseStyle {

  @Override
  public boolean matches(@NonNull String name) {
    // Custom logic to match the naming convention
    return Character.isUpperCase(name.charAt(0)) && name.contains("_");
  }

  @Override
  public String getName() {
    return "MyCustomCaseStyle";
  }
}
```

### Step 2: Build the Project

When you build the project, the annotation processor will automatically
detect the `MyCustomCaseStyle` class and include it in the generated `CaseStyleInjector` class.

## Generated `CaseStyleInjector` Class Example

The generated `CaseStyleInjector` class will look something like this:

```java
package dev.cookiecode.stylesniffer.generated;

import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(value = "dev.cookiecode.codesense.casestyle.processor.CaseStyleAnnotationProcessor", date = "2024-08-17T20:13:32.9769175")
public class CaseStyleInjector {

  public List<Class<? extends CaseStyle>> getAnnotatedCaseStyles() {
    return List.of(
        MyCustomCaseStyle.class,
        // other registered styles...
    );
  }
}

```
