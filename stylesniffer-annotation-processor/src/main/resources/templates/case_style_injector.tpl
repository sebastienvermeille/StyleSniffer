package [[${packageName}]];

[# th:each="importClass : ${imports}"]import [[${importClass}]];
[/]

@Generated(value = "dev.cookiecode.codesense.casestyle.processor.CaseStyleAnnotationProcessor", date = "[[${generatedAt}]]")
public class [[${className}]] {
    public List<Class<? extends CaseStyle>> getAnnotatedCaseStyles() {
        return List.of([# th:each="element,iterStat : ${elements}"]
            [[${element}]].class[[${iterStat.last ? '' : ','}]]
        [/]);
    }
}
