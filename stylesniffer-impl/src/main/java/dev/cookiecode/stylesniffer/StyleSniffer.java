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

import static java.lang.reflect.Modifier.isAbstract;
import static java.util.stream.Collectors.toSet;

import dev.cookiecode.stylesniffer.api.CaseStyle;
import dev.cookiecode.stylesniffer.generated.CaseStyleInjector;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * The {@code StyleSniffer} class is responsible for managing and detecting various {@link
 * CaseStyle} implementations.
 *
 * <p>It registers all case style classes that are annotated with {@link
 * dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle}, instantiates them, and stores them in
 * a list. It also provides methods to retrieve case styles based on names and to list all supported
 * case styles.
 *
 * @author Sebastien Vermeille
 */
public class StyleSniffer {

  private final List<CaseStyle> caseStyles = new ArrayList<>();

  /**
   * Constructs a {@code StyleSniffer} instance and initializes case style recognizers.
   *
   * <p>This constructor attempts to register all case style classes that are annotated with
   * {@code @RegisterCaseStyle}.
   *
   * @throws RuntimeException if there is an error during initialization
   */
  StyleSniffer() {
    try {
      this.registerAllAnnotatedCaseStyles();
    } catch (final Exception e) {
      throw new RuntimeException("Failed to initialize CaseStyleRecognizer", e);
    }
  }

  /**
   * Registers all case style classes that are annotated with {@code @RegisterCaseStyle}.
   *
   * <p>This method retrieves annotated case style classes using the {@link CaseStyleInjector} and
   * instantiates them, adding them to the internal list of case styles. Any instantiation failures
   * are wrapped in a {@code RuntimeException}.
   *
   * @throws RuntimeException if there is an error instantiating case style classes
   */
  private void registerAllAnnotatedCaseStyles() {

    final var annotatedClasses = new CaseStyleInjector().getAnnotatedCaseStyles();

    for (final var clazz : annotatedClasses) {
      if (CaseStyle.class.isAssignableFrom(clazz) && !isAbstract(clazz.getModifiers())) {
        try {
          this.caseStyles.add(clazz.getDeclaredConstructor().newInstance());
        } catch (final Exception e) {
          throw new RuntimeException(
              String.format("Failed to instantiate case style: %s", clazz.getName()), e);
        }
      }
    }
  }

  /**
   * Retrieves a {@link CaseStyle} that matches the given name.
   *
   * <p>This method searches through the registered case styles and returns the first style that
   * matches the provided name.
   *
   * @param name the name to match against case styles
   * @return an {@link Optional} containing the matching {@code CaseStyle} if found, or an empty
   *     {@code Optional} if no match is found
   */
  public Optional<CaseStyle> getCaseStyle(@Nullable final String name) {
    if (name == null || name.isEmpty()) {
      return Optional.empty();
    }

    return this.caseStyles.stream().filter(style -> style.matches(name)).findFirst();
  }

  /**
   * Returns a set of names for all supported case styles.
   *
   * <p>This method provides a set of unique names for the case styles currently recognized by this
   * {@code StyleSniffer}.
   *
   * @return a {@link Set} of names for supported case styles
   */
  public Set<String> getSupportedCaseStyles() {
    return this.caseStyles.stream().map(CaseStyle::getName).collect(toSet());
  }

  /**
   * Returns a set of names for all supported case styles, including all variants.
   *
   * <p>This method provides a comprehensive list of all case style names recognized by this {@code
   * StyleSniffer}, including both primary names and their variants.
   *
   * @return a {@link Set} of names for all supported case styles, including variants
   */
  public Set<String> getSupportedCaseStylesIncludingVariants() {
    return this.caseStyles.stream()
        .flatMap(style -> style.getVariantNames().stream())
        .collect(toSet());
  }
}
