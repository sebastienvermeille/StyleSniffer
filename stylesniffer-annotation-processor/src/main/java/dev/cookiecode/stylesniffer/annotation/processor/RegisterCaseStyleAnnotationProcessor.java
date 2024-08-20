/*
 * The MIT License
 * Copyright © 2024 Sebastien Vermeille
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dev.cookiecode.stylesniffer.annotation.processor;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.util.stream.Collectors.toList;

import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Generated;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import lombok.extern.flogger.Flogger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Processes the dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle annotation, generating a
 * CaseStyleInjector class that registers all dev.cookiecode.stylesniffer.api.CaseStyle
 * implementations.
 *
 * <p>Uses Thymeleaf for code generation and Flogger for logging. Supports Java
 * SourceVersion.RELEASE_21.
 *
 * @author Sebastien Vermeille
 * @see dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle
 * @see dev.cookiecode.stylesniffer.api.CaseStyle
 */
@SupportedAnnotationTypes("dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@Flogger
public class RegisterCaseStyleAnnotationProcessor extends AbstractProcessor {

  private static final String GENERATED_CLASS_PACKAGE_NAME =
      "dev.cookiecode.stylesniffer.generated";
  private static final String GENERATED_CLASS_NAME = "CaseStyleInjector";
  private static final String TEMPLATE_EXTENSION = ".tpl";
  private static final String TEMPLATE_MODE = "TEXT";
  private static final String UTF_8 = "UTF-8";
  private static final String TEMPLATES_DIR = "templates/";
  private static final String TEMPLATE_VARIABLE_PACKAGE_NAME = "packageName";
  private static final String TEMPLATE_VARIABLE_IMPORTS = "imports";
  private static final String TEMPLATE_VARIABLE_CLASS_NAME = "className";
  private static final String TEMPLATE_VARIABLE_ELEMENTS = "elements";
  private static final String TEMPLATE_VARIABLE_GENERATED_AT = "generatedAt";
  private static final String TEMPLATE_FILE_NAME = "case_style_injector";
  private static final String POINT = ".";
  private static final Class<? extends Annotation> ANNOTATION_CLASS = RegisterCaseStyle.class;
  private static final Class<?> IMPLEMENTED_INTERFACE_CLASS = CaseStyle.class;

  @Override
  public boolean process(
      final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
    final var annotatedElements = roundEnv.getElementsAnnotatedWith(ANNOTATION_CLASS);

    if (!annotatedElements.isEmpty()) {
      this.generateCaseStyleInjector(annotatedElements);
    }

    return true; // Indicates that annotations are claimed
  }

  private void generateCaseStyleInjector(final Set<? extends Element> elements) {
    try {
      final var templateEngine = this.configureTemplateEngine();
      final var context = this.prepareTemplateContext(elements);

      final var generatedCode = templateEngine.process(TEMPLATE_FILE_NAME, context);
      this.writeGeneratedClassToFile(generatedCode);

    } catch (final IOException e) {
      log.atSevere().withCause(e).log("Failed to generate the %s class", GENERATED_CLASS_NAME);
    }
  }

  private TemplateEngine configureTemplateEngine() {
    final var templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix(TEMPLATES_DIR);
    templateResolver.setSuffix(TEMPLATE_EXTENSION);
    templateResolver.setTemplateMode(TEMPLATE_MODE);
    templateResolver.setCharacterEncoding(UTF_8);

    final var templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    return templateEngine;
  }

  private Context prepareTemplateContext(final Set<? extends Element> elements) {
    final var context = new Context();
    context.setVariable(TEMPLATE_VARIABLE_PACKAGE_NAME, GENERATED_CLASS_PACKAGE_NAME);

    final var imports =
        Set.of(
            Generated.class.getCanonicalName(),
            List.class.getCanonicalName(),
            ArrayList.class.getCanonicalName(),
            this.IMPLEMENTED_INTERFACE_CLASS.getCanonicalName());
    context.setVariable(TEMPLATE_VARIABLE_IMPORTS, imports.stream().sorted().collect(toList()));
    context.setVariable(TEMPLATE_VARIABLE_CLASS_NAME, GENERATED_CLASS_NAME);

    final var classesList =
        elements.stream()
            .map(element -> ((TypeElement) element).getQualifiedName().toString())
            .collect(toList());

    context.setVariable(TEMPLATE_VARIABLE_ELEMENTS, classesList);

    final var moment = now(UTC).format(ISO_LOCAL_DATE_TIME);
    context.setVariable(TEMPLATE_VARIABLE_GENERATED_AT, moment);

    return context;
  }

  private void writeGeneratedClassToFile(final String generatedCode) throws IOException {
    final var file =
        this.processingEnv
            .getFiler()
            .createSourceFile(GENERATED_CLASS_PACKAGE_NAME + POINT + GENERATED_CLASS_NAME);
    try (final var writer = file.openWriter()) {
      writer.write(generatedCode);
    }
  }
}
