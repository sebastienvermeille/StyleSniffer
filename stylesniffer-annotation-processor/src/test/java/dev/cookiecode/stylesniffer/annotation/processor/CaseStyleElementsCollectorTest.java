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

import static dev.cookiecode.stylesniffer.annotation.processor.CaseStyleElementsCollector.ANNOTATION_CLASS;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
@ExtendWith(MockitoExtension.class)
class CaseStyleElementsCollectorTest {

  @Mock private RoundEnvironment roundEnvironment;

  private CaseStyleElementsCollector caseStyleElementsCollector;

  @BeforeEach
  void setUp() {
    caseStyleElementsCollector = new CaseStyleElementsCollector();
  }

  @Test
  void collectElementsShouldReturnCaseStyleElementsGivenSomeWhereMocked() {

    // GIVEN
    final var name = mock(Name.class);
    doReturn("some").when(name).toString();
    final var mockedElement = mock(TypeElement.class);
    doReturn(name).when(mockedElement).getQualifiedName();
    final var mockedClasses = Set.of(mockedElement);
    doReturn(mockedClasses).when(roundEnvironment).getElementsAnnotatedWith(ANNOTATION_CLASS);

    // WHEN
    var result = caseStyleElementsCollector.collectElements(roundEnvironment);

    // THEN
    assertThat(result).isNotEmpty();
  }

  @Test
  void collectElementsShouldReturnAnEmptyListGivenNoAnnotatedElementsArePresent() {

    // GIVEN
    doReturn(emptySet()).when(roundEnvironment).getElementsAnnotatedWith(ANNOTATION_CLASS);

    // WHEN
    var result = caseStyleElementsCollector.collectElements(roundEnvironment);

    // THEN
    assertThat(result).isNotNull().isEmpty();
  }
}
