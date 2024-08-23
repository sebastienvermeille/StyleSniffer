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

import static dev.cookiecode.stylesniffer.annotation.processor.TemplateRenderer.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
@ExtendWith(MockitoExtension.class)
class TemplateRendererTest {

  @Mock private ProcessorTemplateEngine templateEngine;

  private TemplateRenderer templateRenderer;

  @BeforeEach
  void setUp() {
    templateRenderer = spy(new TemplateRenderer(templateEngine));
  }

  @Test
  void renderTemplateShouldThrowAnIllegalStateExceptionGivenItReceivesAnEmptyListOfElements() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          // GIVEN
          final List<String> emptyElements = emptyList();

          // WHEN
          templateRenderer.renderTemplate(emptyElements);
        });
  }

  @Test
  void renderTemplateShouldInvokePrepareTemplateContextGivenItContainsElements() {
    // GIVEN
    final var elements = List.of("firstElement", "secondElement");

    // WHEN
    templateRenderer.renderTemplate(elements);

    // THEN
    verify(templateRenderer, times(1)).prepareTemplateContext(elements);
  }

  @Test
  void
      renderTemplateShouldInvokeTemplateEngineProcessWithPreviouslyCreatedContextAndTemplateFileNameGivenItContainsElements() {
    // GIVEN
    final var elements = List.of("firstElement", "secondElement");
    final var templateContext = mock(Context.class);
    doReturn(templateContext).when(templateRenderer).prepareTemplateContext(elements);

    // WHEN
    templateRenderer.renderTemplate(elements);

    // THEN
    verify(templateEngine, times(1)).process(TEMPLATE_FILE_NAME, templateContext);
  }

  @Test
  void
      prepareTemplateContextShouldThrowAnIllegalStateExceptionGivenItReceivesAnEmptyListOfElements() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          // GIVEN
          final List<String> emptyElements = emptyList();

          // WHEN
          templateRenderer.prepareTemplateContext(emptyElements);
        });
  }

  @Test
  void prepareTemplateContextShouldPopulateAllContextVariablesProperly() {
    // GIVEN
    final var elements = List.of("firstElement", "secondElement");
    final var contextVariableNames =
        Set.of(PACKAGE_NAME, CLASS_NAME, ELEMENTS, IMPORTS, GENERATED_AT);
    // WHEN
    var actualContext = templateRenderer.prepareTemplateContext(elements);

    // THEN
    assertThat(actualContext).isNotNull();
    assertThat(actualContext.getVariableNames()).containsAll(contextVariableNames);
    for (final var contextVariableName : contextVariableNames) {
      assertThat(actualContext.getVariable(contextVariableName)).isNotNull().isNotEqualTo("");
    }
    assertThat(actualContext.getVariable(ELEMENTS)).isEqualTo(elements);
  }
}
