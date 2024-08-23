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
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

import com.google.common.annotations.VisibleForTesting;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import dev.cookiecode.stylesniffer.api.exception.StyleSnifferException;
import jakarta.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleSnifferImpl implements StyleSniffer {

  private List<CaseStyle> caseStyles = new ArrayList<>();

  StyleSnifferImpl(@NonNull List<Class<? extends CaseStyle>> caseStyleClasses) {
    registerCaseStyleClasses(caseStyleClasses);
  }

  @VisibleForTesting
  void registerCaseStyleClass(@NonNull Class<? extends CaseStyle> caseStyleClass) {
    registerCaseStyleClasses(Collections.singletonList(caseStyleClass));
  }

  @VisibleForTesting
  void registerCaseStyleClasses(@NonNull List<Class<? extends CaseStyle>> caseStyleClasses) {
    List<CaseStyle> newCaseStyles = new ArrayList<>(caseStyles);
    newCaseStyles.addAll(
        caseStyleClasses.stream()
            .filter(this::isValidCaseStyleClass)
            .map(this::instantiateCaseStyle)
            .toList());

    caseStyles =
        unmodifiableList(
            newCaseStyles); // ensure only this method can modify caseStyles items (no add nor
    // delete)
  }

  /**
   * Checks if the given class is a valid {@link CaseStyle} implementation.
   *
   * @param clazz the class to check
   * @return {@code true} if the class is a valid {@code CaseStyle} implementation; {@code false}
   *     otherwise
   */
  @VisibleForTesting
  boolean isValidCaseStyleClass(Class<?> clazz) {
    return CaseStyle.class.isAssignableFrom(clazz) && !isAbstract(clazz.getModifiers());
  }

  /**
   * Instantiates a {@link CaseStyle} class.
   *
   * @param clazz the class to instantiate
   * @return the instantiated {@code CaseStyle}
   * @throws StyleSnifferException if instantiation fails
   */
  @VisibleForTesting
  CaseStyle instantiateCaseStyle(@NonNull Class<? extends CaseStyle> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException e) {
      throw new StyleSnifferException(
          String.format("Cannot instantiate case style class %s. Is it abstract?", clazz.getName()),
          e);
    } catch (IllegalAccessException | NoSuchMethodException e) {
      throw new StyleSnifferException(
          String.format(
              "Access violation while instantiating case style class %s. Does it has a public no args constructor?",
              clazz.getName()),
          e);
    } catch (InvocationTargetException e) {
      throw new StyleSnifferException(
          "Exception thrown by constructor for case style class: " + clazz.getName(), e);
    }
  }

  @Override
  public Optional<CaseStyle> getCaseStyle(@Nullable final String name) {
    return sanitizeInput(name)
        .flatMap(
            sanitizedName ->
                caseStyles.stream().filter(style -> style.matches(sanitizedName)).findFirst());
  }

  @Override
  public Optional<CaseStyle> getCaseStyleWithVariantOrName(@Nullable final String variantOrName) {
    return sanitizeInput(variantOrName)
        .flatMap(
            sanitizedName ->
                caseStyles.stream()
                    .filter(
                        style ->
                            style.getName().equals(sanitizedName)
                                || style.getVariantNames().contains(sanitizedName))
                    .findFirst());
  }

  /**
   * Performs basic sanity checks on the input, such as trimming whitespace.
   *
   * @param name the input name to sanitize
   * @return an {@link Optional} containing the sanitized name, or an empty {@link Optional} if the
   *     input is invalid
   */
  private Optional<String> sanitizeInput(@Nullable final String name) {
    return ofNullable(name).map(String::trim).filter(sanitizedName -> !sanitizedName.isEmpty());
  }

  @Override
  public Set<String> getSupportedCaseStyles() {
    return caseStyles.stream().map(CaseStyle::getName).collect(toSet());
  }

  @Override
  public Set<String> getSupportedCaseStylesIncludingVariants() {
    return caseStyles.stream().flatMap(style -> style.getVariantNames().stream()).collect(toSet());
  }
}
