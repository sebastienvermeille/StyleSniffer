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

import static dev.cookiecode.stylesniffer.annotation.processor.RegisterCaseStyleAnnotationProcessor.GENERATED_CLASS_NAME;
import static dev.cookiecode.stylesniffer.annotation.processor.RegisterCaseStyleAnnotationProcessor.GENERATED_CLASS_PACKAGE_NAME;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import com.google.common.annotations.VisibleForTesting;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;

/**
 * Handles the rendering of templates using Thymeleaf.
 *
 * <p>This class is responsible for setting up the template engine, preparing the context with
 * necessary variables, and rendering the final template into a string.
 *
 * <p>It uses the {@code case_style_injector.tpl} template to generate the source code.
 *
 * @author Sebastien Vermeille
 * @see org.thymeleaf.context.Context
 */
@RequiredArgsConstructor()
public class TemplateRenderer {

  static final String TEMPLATE_FILE_NAME = "case_style_injector";

  // Template variable constants
  static final String PACKAGE_NAME = "packageName";
  static final String CLASS_NAME = "className";
  static final String ELEMENTS = "elements";
  static final String IMPORTS = "imports";
  static final String GENERATED_AT = "generatedAt";

  private final ProcessorTemplateEngine templateEngine;

  /**
   * Renders the template with the given list of elements.
   *
   * @param elements List of fully qualified class names to include in the generated class.
   * @return Rendered template as a string.
   */
  public String renderTemplate(@NonNull List<String> elements) {
    if (elements.isEmpty()) {
      throw new IllegalStateException(
          "Cannot render empty elements, upper layer should have prevented this to occurs.");
    }

    final var context = prepareTemplateContext(elements);
    return templateEngine.process(TEMPLATE_FILE_NAME, context);
  }

  @VisibleForTesting
  Context prepareTemplateContext(@NonNull List<String> elements) {
    if (elements.isEmpty()) {
      throw new IllegalStateException(
          "Cannot build context for empty elements, upper layer should have prevented this to occurs.");
    }

    final var context = new Context();
    context.setVariable(PACKAGE_NAME, GENERATED_CLASS_PACKAGE_NAME);
    context.setVariable(CLASS_NAME, GENERATED_CLASS_NAME);
    context.setVariable(ELEMENTS, elements);
    context.setVariable(
        IMPORTS,
        Set.of(
                Generated.class.getCanonicalName(),
                List.class.getCanonicalName(),
                ArrayList.class.getCanonicalName(),
                CaseStyle.class.getCanonicalName())
            .stream()
            .sorted()
            .toList());
    context.setVariable(GENERATED_AT, now(UTC).format(ISO_LOCAL_DATE_TIME));
    return context;
  }
}
