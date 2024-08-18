# StyleSniffer API

## Overview

The `stylesniffer-api` module defines the core API for representing and working with naming
conventions, which are commonly referred to as case styles (e.g., `camelCase`, `PascalCase`). This
module provides an interface that allows other modules or applications to match and identify
specific naming patterns in strings, such as variable names or class names.

## Responsibilities

- Provides the `CaseStyle` interface, which represents a naming convention and defines methods to:
    - Match a string against the convention.
    - Retrieve the display name of the convention.
    - Retrieve any variant names associated with the convention.

This API is intended to be implemented by various naming convention classes, allowing the detection
and enforcement of consistent naming styles in codebases.

## Key Interface: `CaseStyle`

### `CaseStyle` Interface

```java
public interface CaseStyle {

    boolean matches(@NonNull String name);

    String getName();

    default Set<String> getVariantNames() {
        return Set.of(this.getName());
    }
}