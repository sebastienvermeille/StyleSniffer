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

import static lombok.AccessLevel.*;

import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import lombok.*;
import lombok.extern.flogger.Flogger;

/**
 * Annotation processor for {@link dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle}.
 *
 * <p>This processor scans for classes annotated with {@code @RegisterCaseStyle}, collects them, and
 * generates a {@code CaseStyleInjector} class using a template.
 *
 * <p>The actual processing tasks are delegated to {@link CaseStyleElementsCollector}, {@link
 * TemplateRenderer}, and {@link FileWriter}, promoting the Single Responsibility Principle.
 *
 * @author Sebastien Vermeille
 * @see dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle
 * @see dev.cookiecode.stylesniffer.api.CaseStyle
 */
@NoArgsConstructor(access = PUBLIC)
@AllArgsConstructor(access = PACKAGE)
@Getter(value = PACKAGE)
@SupportedAnnotationTypes("dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@Flogger
public class RegisterCaseStyleAnnotationProcessor extends AbstractProcessor {

  // Common constants shared across classes
  public static final String GENERATED_CLASS_PACKAGE_NAME = "dev.cookiecode.stylesniffer.generated";
  public static final String GENERATED_CLASS_NAME = "CaseStyleInjector";

  private TemplateRenderer templateRenderer;
  private FileWriter fileWriter;
  private CaseStyleElementsCollector elementsCollector;

  @Override
  public synchronized void init(@NonNull ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    final var templateEngine = new ProcessorTemplateEngine();
    this.templateRenderer = new TemplateRenderer(templateEngine);
    this.fileWriter = new FileWriter(processingEnv);
    this.elementsCollector = new CaseStyleElementsCollector();
  }

  @Override
  public boolean process(
      @NonNull Set<? extends TypeElement> annotations, @NonNull RoundEnvironment roundEnv) {
    try {
      final var elements = elementsCollector.collectElements(roundEnv);
      if (!elements.isEmpty()) {
        final var generatedCode = templateRenderer.renderTemplate(elements);
        fileWriter.writeToFile(generatedCode);
      }
    } catch (IOException e) {
      log.atSevere().withCause(e).log("Failed to generate the %s class", GENERATED_CLASS_NAME);
    }

    return true;
  }
}
