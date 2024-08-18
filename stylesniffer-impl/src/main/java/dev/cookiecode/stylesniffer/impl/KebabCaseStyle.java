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
package dev.cookiecode.stylesniffer.impl;

import static java.lang.Character.isLowerCase;
import static java.util.Set.of;

import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.Set;
import lombok.NonNull;

/**
 * {@link CaseStyle} implementation that checks for Kebab Case naming style.
 *
 * <p>Kebab Case style is characterized by all lowercase letters and dash separating words.
 *
 * @author Sebastien Vermeille
 */
@RegisterCaseStyle
public class KebabCaseStyle implements CaseStyle {

  private static final char SEPARATOR = '-';

  /**
   * Checks if the given name matches the snake_case style.
   *
   * @param name the name to check
   * @return {@code true} if the name is in snake_case, {@code false} otherwise
   */
  @Override
  public boolean matches(@NonNull final String name) {
    return this.isKebabCase(name);
  }

  @Override
  public String getName() {
    return "Kebab Case";
  }

  @Override
  public Set<String> getVariantNames() {
    return of(
        this.getName(),
        "caterpillar-case",
        "param-case",
        "dash-case",
        "hyphen-case",
        "lisp-case",
        "spinal-case",
        "css-case");
  }

  /**
   * Determines if the given name is in snake_case style.
   *
   * <p>Checks if the name consists entirely of lowercase letters and underscores, with at least one
   * underscore present, and no other characters or uppercase letters.
   *
   * @param name the name to check
   * @return {@code true} if the name is in snake_case, {@code false} otherwise
   */
  private boolean isKebabCase(@NonNull final String name) {
    // Check if the name is all lowercase and contains underscores
    boolean hasDash = false;
    for (int i = 0; i < name.length(); i++) {
      final char c = name.charAt(i);
      if (c == SEPARATOR) {
        hasDash = true;
      } else if (!isLowerCase(c)) {
        return false;
      }
    }
    return hasDash;
  }
}
