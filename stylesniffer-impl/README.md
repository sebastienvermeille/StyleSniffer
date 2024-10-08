# StyleSniffer Implementation Module

## Overview

The `stylesniffer-impl` module contains the core implementation of the StyleSniffer utility
and various CaseStyle naming convention checkers.

This module handles the logic for recognizing different naming styles (such as camelCase,
PascalCase,
snake_case, ...).

## Responsibilities

- **CaseStyle Implementations**: Provides implementations of different naming conventions such as
  CamelCaseStyle.
- **StyleSniffer Core**: Manages the registration and retrieval of CaseStyle implementations.
- **Factory Utility**: Creates instances of StyleSniffer.

## Key Components

### `CaseStyle` Implementations

This module includes several implementations of the CaseStyle interface,
each representing a specific naming convention. For example:

`CamelCaseStyle`

```java

@RegisterCaseStyle
public class CamelCaseStyle implements CaseStyle {

  @Override
  public boolean matches(@NonNull String name) {
    return this.isCamelCase(name);
  }

  @Override
  public String getName() {
    return "camelCase";
  }

  @Override
  public Set<String> getVariantNames() {
    return Set.of(this.getName(), "LowerCamelCase");
  }

  private boolean isCamelCase(@NonNull final String name) {
    // CamelCase validation logic
  }
}
```

### `StyleSniffer` Class

The `StyleSniffer` class manages all CaseStyle implementations.
It automatically registers all classes annotated with `@RegisterCaseStyle` using the
`CaseStyleInjector` generated by the annotation processor.

Key methods include:

- **getCaseStyle(String name)**: Returns the matching CaseStyle for a given name.
- **getSupportedCaseStyles()**: Lists all supported CaseStyle names.
- **getSupportedCaseStylesIncludingVariants()**: Lists all CaseStyle names including their variants.

### `StyleSnifferFactory`

The `StyleSnifferFactory` is a utility class that provides a static method
to create instances of `StyleSniffer`:

```java
public final class StyleSnifferFactory {

  public static StyleSniffer createStyleSniffer() {
    return new StyleSniffer();
  }
}
```
