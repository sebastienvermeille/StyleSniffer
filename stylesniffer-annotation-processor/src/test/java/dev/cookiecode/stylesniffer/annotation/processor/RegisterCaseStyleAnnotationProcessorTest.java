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

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
@ExtendWith(MockitoExtension.class)
class RegisterCaseStyleAnnotationProcessorTest {

  @Mock private TemplateRenderer templateRenderer;

  @Mock private FileWriter fileWriter;

  @Mock private CaseStyleElementsCollector caseStyleElementsCollector;

  @InjectMocks private RegisterCaseStyleAnnotationProcessor processor;

  @Test
  void processShouldReturnTrueEvenIfNoElementsWereRegistered() {
    // GIVEN
    final var roundEnv = noCaseStyleAnnotatedElements();

    // WHEN
    final var actualResult = processor.process(emptySet(), roundEnv);

    // THEN
    assertThat(actualResult).isTrue();
  }

  @Test
  void processShouldReturnTrueEvenIfAnIOExceptionOccurs() throws Exception {
    // GIVEN
    final var elements = List.of("DummyStyle", "SuperDummyPlusStyle");
    final var roundEnv = caseStyleAnnotatedElements(elements);

    final var generatedCode = "some code;";
    doReturn(generatedCode).when(templateRenderer).renderTemplate(elements);

    doThrow(new IOException("File writer error")).when(fileWriter).writeToFile(generatedCode);

    // WHEN
    final var actualResult = processor.process(emptySet(), roundEnv);

    // THEN
    assertThat(actualResult).isTrue();
  }

  @Test
  void processShouldNotInteractWithTemplateRendererGivenNoElementsAreCollected() {
    // GIVEN
    final var mockedRoundEnv = noCaseStyleAnnotatedElements();

    // WHEN
    processor.process(emptySet(), mockedRoundEnv);

    // THEN
    verifyNoInteractions(templateRenderer);
  }

  @Test
  void processShouldNotInteractWithFileWriterGivenNoElementsAreCollected() {
    // GIVEN
    final var mockedRoundEnv = noCaseStyleAnnotatedElements();

    // WHEN
    processor.process(emptySet(), mockedRoundEnv);

    // THEN
    verifyNoInteractions(fileWriter);
  }

  @Test
  void processShouldProvideRetrievedElementsToTemplateRendererGivenThereAreElements() {
    // GIVEN
    final var elements = List.of("DummyStyle", "SuperDummyPlusStyle");
    final var roundEnv = caseStyleAnnotatedElements(elements);

    // WHEN
    processor.process(emptySet(), roundEnv);

    // THEN
    verify(templateRenderer, times(1)).renderTemplate(elements);
  }

  @Test
  void processShouldProvideGeneratedCodeFileWriterGivenSomeCodeWereGenerated() throws Exception {
    // GIVEN
    final var elements = List.of("DummyStyle", "SuperDummyPlusStyle");
    final var roundEnv = caseStyleAnnotatedElements(elements);
    final var generatedCode = "Generated code;";
    doReturn(generatedCode).when(templateRenderer).renderTemplate(elements);

    // WHEN
    processor.process(emptySet(), roundEnv);

    // THEN
    verify(fileWriter, times(1)).writeToFile(generatedCode);
  }

  @Test
  void initShouldInitAllRequiredFields() {
    // GIVEN
    final var processingEnv = mock(ProcessingEnvironment.class);
    final var processorWithoutInjectedMocks = new RegisterCaseStyleAnnotationProcessor();

    // WHEN
    processorWithoutInjectedMocks.init(processingEnv);

    // THEN
    assertThat(processorWithoutInjectedMocks.getTemplateRenderer()).isNotNull();
    assertThat(processorWithoutInjectedMocks.getFileWriter()).isNotNull();
    assertThat(processorWithoutInjectedMocks.getElementsCollector()).isNotNull();
  }

  private RoundEnvironment noCaseStyleAnnotatedElements() {
    final var roundEnv = mock(RoundEnvironment.class);
    final var noElements = emptyList();
    doReturn(noElements).when(caseStyleElementsCollector).collectElements(roundEnv);
    return roundEnv;
  }

  private RoundEnvironment caseStyleAnnotatedElements(@NonNull List<String> caseStyleNames) {
    final var roundEnv = mock(RoundEnvironment.class);
    doReturn(caseStyleNames).when(caseStyleElementsCollector).collectElements(roundEnv);
    return roundEnv;
  }
}
