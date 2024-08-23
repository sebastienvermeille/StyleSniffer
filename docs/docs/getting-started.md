---
sidebar_position: 2
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Getting Started

## Add the dependency

<Tabs
values={[
{ label: 'Maven', value: 'maven' },
{ label: 'Gradle (Kotlin)', value: 'gradle' },
]}
>
    <TabItem value="maven">

**pom.xml**
```xml
<dependency>
    <groupId>dev.cookiecode</groupId>
    <artifactId>stylesniffer</artifactId>
    <version>1.0.0</version>
</dependency>
```

    </TabItem>
    <TabItem value="gradle">

**gradle.properties**
```kotlin
implementation("dev.cookiecode:stylesniffer-impl:1.0.0")
```

    </TabItem>
</Tabs>

> Note: Use the latest version: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.cookiecode/stylesniffer-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.cookiecode/stylesniffer-parent)

## Use StyleSniffer in your code

<Tabs
values={[
{ label: 'Java', value: 'java' },
{ label: 'Kotlin', value: 'kotlin' },
]}
>
    <TabItem value="java">

```java
// Create an instance of StyleSniffer
var styleSniffer = StyleSnifferFactory.createStyleSniffer();

// Retrieve a CaseStyle and handle the result
Optional<CaseStyle> caseStyle = styleSniffer.getCaseStyle("myVariableName");
caseStyle.ifPresent(style -> System.out.println("Matched style: " + style.getName()));

// Retrieve and print supported case styles
Set<String> supportedStyles = styleSniffer.getSupportedCaseStyles();
System.out.println("Supported styles: " + supportedStyles);
```

    </TabItem>
    <TabItem value="kotlin">

**gradle.properties**
```kotlin
// Create an instance of StyleSniffer
val styleSniffer = StyleSnifferFactory.createStyleSniffer()

// Retrieve a CaseStyle and handle the result
val caseStyle: CaseStyle? = styleSniffer.getCaseStyle("myVariableName")
caseStyle?.let {
    println("Matched style: ${it.getName()}")
}

// Retrieve and print supported case styles
val supportedStyles: Set<String> = styleSniffer.getSupportedCaseStyles()
println("Supported styles: $supportedStyles")
```

    </TabItem>
</Tabs>
