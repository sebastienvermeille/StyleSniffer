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

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.cookiecode.stylesniffer.api.exception.StyleSnifferException;
import dev.cookiecode.stylesniffer.generated.CaseStyleInjector;
import dev.cookiecode.stylesniffer.impl.casestyle.PascalCaseStyle;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
class StyleSnifferImplTest {

  private StyleSnifferImpl styleSniffer;

  @BeforeEach
  void setUp() {
    styleSniffer = new StyleSnifferImpl(emptyList());
  }

  @Test
  void isValidCaseStyleClassShouldReturnTrueGivenTheClassImplementsCaseStyleAndIsNotAbstract() {
    // GIVEN
    final var clazz = PascalCaseStyle.class;

    // WHEN
    final var actualResult = styleSniffer.isValidCaseStyleClass(clazz);

    // THEN
    assertThat(actualResult).isTrue();
  }

  @Test
  void isValidCaseStyleClassShouldReturnFalseGivenTheClassImplementsCaseStyleButIsAbstract() {
    // GIVEN
    final var clazz = AbstractCaseStyle.class;

    // WHEN
    final var actualResult = styleSniffer.isValidCaseStyleClass(clazz);

    // THEN
    assertThat(actualResult).isFalse();
  }

  @Test
  void isValidCaseStyleClassShouldReturnFalseGivenTheClassDoNotImplementsCaseStyle() {
    // GIVEN
    final var clazz = ArrayList.class;

    // WHEN
    final var actualResult = styleSniffer.isValidCaseStyleClass(clazz);

    // THEN
    assertThat(actualResult).isFalse();
  }

  @Test
  void instantiateCaseStyleShouldThrowStyleSnifferExceptionInCaseOfAccessViolation() {

    var thrown =
        assertThrows(
            StyleSnifferException.class,
            () -> {
              // GIVEN
              final var clazz = FaultyCaseStyleWithoutPublicConstructor.class;

              // WHEN
              styleSniffer.instantiateCaseStyle(clazz);
            });
    assertThat(thrown.getMessage()).contains("Does it has a public no args constructor?");
  }

  @Test
  void instantiateCaseStyleShouldThrowStyleSnifferExceptionInCaseOfNoEmptyConstructor() {

    var thrown =
        assertThrows(
            StyleSnifferException.class,
            () -> {
              // GIVEN
              final var clazz = FaultyCaseStyleWithoutPublicNoArgsConstructor.class;

              // WHEN
              styleSniffer.instantiateCaseStyle(clazz);
            });
    assertThat(thrown.getMessage()).contains("Does it has a public no args constructor?");
  }

  @Test
  void instantiateCaseStyleShouldThrowStyleSnifferExceptionInCaseOfAbstractClass() {

    var thrown =
        assertThrows(
            StyleSnifferException.class,
            () -> {
              // GIVEN
              final var clazz = AbstractCaseStyle.class;

              // WHEN
              styleSniffer.instantiateCaseStyle(clazz);
            });
    assertThat(thrown.getMessage()).contains("Is it abstract?");
  }

  @Test
  void
      instantiateCaseStyleShouldThrowStyleSnifferExceptionInCaseTheConstructorGeneratesAnException() {

    assertThrows(
        StyleSnifferException.class,
        () -> {
          // GIVEN
          final var clazz = FaultyCaseStyleWithRuntimeException.class;

          // WHEN
          styleSniffer.instantiateCaseStyle(clazz);
        });
  }

  @Test
  void instantiateCaseStyleShouldReturnANewInstanceInCaseOfSuccess() {

    // GIVEN
    final var clazz = PascalCaseStyle.class;

    // WHEN
    final var actualResult = styleSniffer.instantiateCaseStyle(clazz);

    // THEN
    assertThat(actualResult).isInstanceOf(PascalCaseStyle.class);
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

    final var pascalCaseImplClass = PascalCaseStyle.class;
    styleSniffer.registerCaseStyleClass(pascalCaseImplClass);

    // WHEN
    final var result = styleSniffer.getCaseStyle(pascalCaseInput);

    // THEN
    assertThat(result).isPresent().get().isInstanceOf(pascalCaseImplClass);
  }

  @Test
  void ggetCaseStyleWithVariantOrNameShouldReturnAnEmptyResultGivenNullInput() {
    // GIVEN
    final String nullInput = null;

    // WHEN
    final var result = styleSniffer.getCaseStyleWithVariantOrName(nullInput);

    // THEN
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void getCaseStyleWithVariantOrNameShouldReturnAnEmptyResultGivenEmptyInput() {
    // GIVEN
    final var emptyInput = "";

    // WHEN
    final var result = styleSniffer.getCaseStyleWithVariantOrName(emptyInput);

    // THEN
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void getCaseStyleWithVariantOrNameShouldReturnAnEmptyResultGivenSpaceInput() {
    // GIVEN
    final var spaceInput = " ";

    // WHEN
    final var result = styleSniffer.getCaseStyleWithVariantOrName(spaceInput);

    // THEN
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void
      getCaseStyleWithVariantOrNameShouldReturnPascalCaseCaseStyleGivenInputIsUpperCamelCaseVariant() {
    // GIVEN
    final String variantName = "UpperCamelCase";
    styleSniffer = new StyleSnifferImpl(new CaseStyleInjector().getAnnotatedCaseStyles());

    // WHEN
    final var result = styleSniffer.getCaseStyleWithVariantOrName(variantName);

    // THEN
    assertThat(result).isNotNull().isPresent().get().isInstanceOf(PascalCaseStyle.class);
  }

  @Test
  void getCaseStyleWithVariantOrNameShouldReturnPascalCaseCaseStyleGivenInputIsUpperCamelCase() {
    // GIVEN
    final String name = "PascalCase";
    styleSniffer = new StyleSnifferImpl(new CaseStyleInjector().getAnnotatedCaseStyles());

    // WHEN
    final var result = styleSniffer.getCaseStyleWithVariantOrName(name);

    // THEN
    assertThat(result).isNotNull().isPresent().get().isInstanceOf(PascalCaseStyle.class);
  }

  @Test
  void
      getSupportedCaseStylesShouldReturnANonEmptyListOfSupportedCaseStylesGivenSomeImplementationExists() {
    // GIVEN
    // case embedded styles implementations
    styleSniffer.registerCaseStyleClasses(new CaseStyleInjector().getAnnotatedCaseStyles());

    // WHEN
    final var actuallySupportedCaseStyle = styleSniffer.getSupportedCaseStyles();

    // THEN
    assertThat(actuallySupportedCaseStyle).isNotEmpty();
  }

  @Test
  void getSupportedCaseStylesShouldContainsPascalCaseGivenPascalCaseImplementationExist() {
    // GIVEN
    // case embedded styles implementations
    styleSniffer.registerCaseStyleClasses(new CaseStyleInjector().getAnnotatedCaseStyles());

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
    // case embedded styles implementations
    styleSniffer.registerCaseStyleClasses(new CaseStyleInjector().getAnnotatedCaseStyles());

    final var pascalCaseStyle = new PascalCaseStyle();

    // WHEN
    final var actuallySupportedCaseStyle = styleSniffer.getSupportedCaseStylesIncludingVariants();

    // THEN
    assertThat(actuallySupportedCaseStyle)
        .containsAll(pascalCaseStyle.getVariantNames())
        .contains(pascalCaseStyle.getName());
  }
}
