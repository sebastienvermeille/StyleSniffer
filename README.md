## **StyleSniffer: The Ultimate Case Style Detective**

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.cookiecode/stylesniffer-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.cookiecode/stylesniffer-parent)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![join discord](https://img.shields.io/badge/join%20discord-gray?style=flat&logo=discord&link=https://discord.gg/uqQ2SWCQCb)](https://discord.gg/uqQ2SWCQCb)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sebastienvermeille_stylesniffer&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sebastienvermeille_stylesniffer)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sebastienvermeille_stylesniffer&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sebastienvermeille_stylesniffer)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=sebastienvermeille_stylesniffer&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=sebastienvermeille_stylesniffer)
[![code style](https://img.shields.io/badge/code%20style-google%20code%20style-green?style=flat&link=https://google.github.io/styleguide/javaguide.html)](https://google.github.io/styleguide/javaguide.html)
[![OpenSSF Best Practices](https://bestpractices.coreinfrastructure.org/projects/9374/badge)](https://bestpractices.coreinfrastructure.org/projects/9374)


![StyleSniffer Logo](/docs/static/assets/stylesniffer-logo.png)

**StyleSniffer** is a Java library crafted to simplify the detection and analysis of case styles
in strings.

Whether you're dealing with camelCase, snake_case, PascalCase, or any other naming convention,
StyleSniffer provides a straightforward and efficient way to understand and manage these styles.

### **Features:**

- **Detect Case Styles:** Automatically identify common case styles such as camelCase,
  snake_case, PascalCase, and more.
- **Flexible Integration:** Easily integrate StyleSniffer into your Java applications
- **Simple API:** Enjoy a user-friendly API designed for seamless detection.

### **Why StyleSniffer?**

StyleSniffer excels at uncovering the subtle nuances of string case styles.

- **Playful Precision:** Experience the blend of technical accuracy with a hint of fun.
- **Easy to Use:** Whether you're a seasoned developer or just starting out,
  StyleSniffer's intuitive interface makes case style detection straightforward.
- **Reliable:** Depend on StyleSniffer for consistent and accurate results.

### **Get Started**

```java
StyleSniffer styleSniffer = StyleSnifferFactory.createStyleSniffer();

Optional<CaseStyle> caseStyle = styleSniffer.getCaseStyle("myVariableName");
caseStyle.ifPresent(style -> System.out.println("Matched style: " + style.getName()));

Set<String> supportedStyles = styleSniffer.getSupportedCaseStyles();
System.out.println("Supported styles: " + supportedStyles);
```

Visit the online documentation: https://stylesniffer.cookiecode.dev/
