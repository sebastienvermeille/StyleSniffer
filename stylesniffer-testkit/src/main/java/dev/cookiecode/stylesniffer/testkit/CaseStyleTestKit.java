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
package dev.cookiecode.stylesniffer.testkit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * A base interface for testing implementations of the {@link CaseStyle} interface. This interface
 * provides a common structure for validating whether a given string adheres to a specific case
 * style, such as camelCase, snake_case, or others.
 *
 * <p>Implementers of this interface must provide a concrete implementation of the {@link CaseStyle}
 * to be tested, as well as lists of valid and invalid input strings that correspond to the expected
 * case style. The interface then performs tests to ensure the correctness of the case style
 * implementation.
 *
 * <h2>Responsibilities of the Implementing Class</h2>
 *
 * <p>The implementing class must:
 *
 * <ul>
 *   <li>Provide a concrete {@link CaseStyle} instance via the {@link #createCaseStyle()} method.
 *   <li>Provide a list of valid input strings for the case style via the {@link #matchingInputs()}
 *       method.
 *   <li>Provide a list of invalid input strings for the case style via the {@link
 *       #nonMatchingInputs()} method.
 * </ul>
 *
 * <h2>Test Methods</h2>
 *
 * <p>The interface includes several default test methods:
 *
 * <ul>
 *   <li>{@link #getNameShouldReturnAValidName()}: Verifies that the {@code getName()} method of the
 *       {@link CaseStyle} implementation returns a non-blank string.
 *   <li>{@link #matchesShouldReturnTrueGivenMatchingInputValue(String)}: Uses a parameterized test
 *       to verify that each valid input is correctly recognized by the case style implementation.
 *   <li>{@link #nonMatchingInputShouldNotBeRecognized(String)}: Uses a parameterized test to verify
 *       that each invalid input is correctly rejected by the case style implementation.
 * </ul>
 *
 * <h2>Usage Example</h2>
 *
 * <pre>{@code
 * public class CamelCaseStyleTest implements CaseStyleTestKit<CamelCaseStyle> {
 *
 *     @Override
 *     public CamelCaseStyle createCaseStyle() {
 *         return new CamelCaseStyle();
 *     }
 *
 *     @Override
 *     public List<String> validInputs() {
 *         return List.of("camelCase", "anotherExample", "yetAnotherExample");
 *     }
 *
 *     @Override
 *     public List<String> invalidInputs() {
 *         return List.of("snake_case", "kebab-case", "PascalCase", "Invalid Input");
 *     }
 * }
 * }</pre>
 *
 * <p>This interface leverages JUnit 5's {@link ParameterizedTest} to ensure that the inputs are
 * tested individually and reported clearly. Implementers only need to focus on providing the
 * specific inputs and case style logic.
 *
 * @param <T> the type of {@link CaseStyle} that this test interface will work with
 * @author Sebastien Vermeille
 */
@TestInstance(PER_CLASS)
@SuppressWarnings("unused") // Used by implementers in other Maven modules
public interface CaseStyleTestKit<T extends CaseStyle> {

  T createCaseStyle();

  /**
   * Provide a list of valid inputs for the CaseStyle implementation. The implementer needs to
   * override this method.
   *
   * @return a list of valid inputs
   */
  List<String> matchingInputs();

  /**
   * Provide a list of invalid inputs for the CaseStyle implementation. The implementer needs to
   * override this method.
   *
   * @return a list of invalid inputs
   */
  List<String> nonMatchingInputs();

  @Test
  @SuppressWarnings("unused") // Used at runtime by Junit
  default void getNameShouldReturnAValidName() {
    final var caseStyleImplementation = this.createCaseStyle();

    assertThat(caseStyleImplementation.getName())
        .as("Each CaseStyle implementation must return a value for getName()")
        .isNotBlank();
  }

  @Test
  @SuppressWarnings("unused") // Used at runtime by Junit
  default void getVariantNamesShouldReturnAtLeastTheName() {
    final var caseStyleImplementation = this.createCaseStyle();

    assertThat(caseStyleImplementation.getVariantNames())
        .isNotEmpty()
        .contains(caseStyleImplementation.getName());
  }

  @ParameterizedTest
  @MethodSource("matchingNames")
  @SuppressWarnings("unused") // Used at runtime by Junit
  default void matchesShouldReturnTrueGivenMatchingInputValue(final String matchingInput) {
    final var caseStyleImplementation = this.createCaseStyle();

    assertThat(caseStyleImplementation.matches(matchingInput))
        .as("Expected match with input: %s", matchingInput)
        .isTrue();
  }

  @ParameterizedTest
  @MethodSource("nonMatchingNames")
  @SuppressWarnings("unused") // Used at runtime by Junit
  default void nonMatchingInputShouldNotBeRecognized(final String nonMatchingInput) {
    final var caseStyleImplementation = this.createCaseStyle();

    assertThat(caseStyleImplementation.matches(nonMatchingInput))
        .as("Expected non match with input: %s", nonMatchingInput)
        .isFalse();
  }

  /**
   * Converts the list of valid inputs into a stream. This is needed for the @MethodSource
   * annotation.
   */
  default Stream<String> matchingNames() {
    return this.matchingInputs().stream();
  }

  /**
   * Converts the list of invalid inputs into a stream. This is needed for the @MethodSource
   * annotation.
   */
  default Stream<String> nonMatchingNames() {
    return this.nonMatchingInputs().stream();
  }
}
