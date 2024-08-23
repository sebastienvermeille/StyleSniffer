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
package dev.cookiecode.stylesniffer.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
class BaseCaseStyleTest {

  @Test
  void equalsShouldReturnFalseGivenTwoDifferentCaseStylesAreProvided() {

    // GIVEN
    final var firstCaseStyle = new CaseStyleHavingNoVariantNamesImpl();
    final var secondCaseStyle = new CaseStyleHavingTwoVariantNamesImpl();

    // WHEN
    final var actualResult = firstCaseStyle.equals(secondCaseStyle);

    // THEN
    assertThat(actualResult).isFalse();
  }

  @Test
  void equalsShouldReturnFalseGivenNullIsProvided() {

    // GIVEN
    final var firstCaseStyle = new CaseStyleHavingNoVariantNamesImpl();

    // WHEN
    final var actualResult = firstCaseStyle.equals(null);

    // THEN
    assertThat(actualResult).isFalse();
  }

  @Test
  void equalsShouldReturnTrueGivenTheSameInstanceIsProvidedTwice() {

    // GIVEN
    final var firstCaseStyle = new CaseStyleHavingNoVariantNamesImpl();

    // WHEN
    final var actualResult = firstCaseStyle.equals(firstCaseStyle);

    // THEN
    assertThat(actualResult).isTrue();
  }

  @Test
  void equalsShouldReturnTrueGivenTwoInstancesOfTheSameCaseStyleAreCompared() {

    // GIVEN
    final var firstCaseStyle = new CaseStyleHavingNoVariantNamesImpl();
    final var secondCaseStyle = new CaseStyleHavingNoVariantNamesImpl();

    // WHEN
    final var actualResult = firstCaseStyle.equals(secondCaseStyle);

    // THEN
    assertThat(actualResult).isTrue();
  }

  @Test
  void hashCodeShouldReturnAUniqueValueBasedOnTheNameGivenACaseStyleIsProvided() {
    // GIVEN
    final var caseStyleInstance = new CaseStyleHavingNoVariantNamesImpl();

    // WHEN
    final var actualResult = caseStyleInstance.hashCode();

    // THEN
    assertThat(actualResult).isEqualTo(caseStyleInstance.getName().hashCode());
  }
}
