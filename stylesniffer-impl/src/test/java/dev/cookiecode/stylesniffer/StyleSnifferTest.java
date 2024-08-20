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
package dev.cookiecode.stylesniffer;

import static org.assertj.core.api.Assertions.assertThat;

import dev.cookiecode.stylesniffer.impl.PascalCaseStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test class */
class StyleSnifferTest {

  private StyleSniffer styleSniffer;

  @BeforeEach
  void setUp() {
    styleSniffer = new StyleSniffer();
  }

  @Test
  void getCaseStyleShouldReturnAnEmptyResultGivenNullInput() {
    // GIVEN
    final String nullInput = null;

    // WHEN
    final var result = styleSniffer.getCaseStyle(nullInput);

    // THEN
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void getCaseStyleShouldReturnAnEmptyResultGivenEmptyInput() {
    // GIVEN
    final var emptyInput = "";

    // WHEN
    final var result = styleSniffer.getCaseStyle(emptyInput);

    // THEN
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void getCaseStyleShouldReturnAnEmptyResultGivenSpaceInput() {
    // GIVEN
    final var spaceInput = " ";

    // WHEN
    final var result = styleSniffer.getCaseStyle(spaceInput);

    // THEN
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void getCaseStyleShouldReturnAPascalCaseCaseStyleGivenInputIsWrittenInPascalCase() {
    // GIVEN
    final var pascalCaseInput = "SomePascalCase";

    // WHEN
    final var result = styleSniffer.getCaseStyle(pascalCaseInput);

    // THEN
    assertThat(result).isPresent().get().hasSameClassAs(new PascalCaseStyle());
  }

  @Test
  void
      getSupportedCaseStylesShouldReturnANonEmptyListOfSupportedCaseStylesGivenSomeImplementationExists() {
    // GIVEN
    // case styles implementations

    // WHEN
    final var actuallySupportedCaseStyle = styleSniffer.getSupportedCaseStyles();

    // THEN
    assertThat(actuallySupportedCaseStyle).isNotEmpty();
  }

  @Test
  void getSupportedCaseStylesShouldContainsPascalCaseGivenPascalCaseImplementationExist() {
    // GIVEN
    final var pascalCaseStyleName = new PascalCaseStyle().getName();

    // WHEN
    final var actuallySupportedCaseStyle = styleSniffer.getSupportedCaseStyles();

    // THEN
    assertThat(actuallySupportedCaseStyle).contains(pascalCaseStyleName);
  }

  @Test
  void
      getSupportedCaseStylesIncludingVariantsShouldContainsPascalCaseAndAllItsVariantsGivenPascalCaseImplementationExist() {
    // GIVEN
    final var pascalCaseStyle = new PascalCaseStyle();

    // WHEN
    final var actuallySupportedCaseStyle = styleSniffer.getSupportedCaseStylesIncludingVariants();

    // THEN
    assertThat(actuallySupportedCaseStyle).containsAll(pascalCaseStyle.getVariantNames());
    assertThat(actuallySupportedCaseStyle).contains(pascalCaseStyle.getName());
  }
}
