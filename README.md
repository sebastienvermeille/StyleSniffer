## **StyleSniffer: The Ultimate Case Style Detective**

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
