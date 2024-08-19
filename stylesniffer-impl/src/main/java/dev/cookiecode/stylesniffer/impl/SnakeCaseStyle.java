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

import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.Set;
import lombok.NonNull;

/**
 * {@link CaseStyle} implementation that checks for snake_case naming style.
 *
 * <p>Snake_case style is characterized by all lowercase letters and underscores separating words.
 *
 * @author Sebastien Vermeille
 */
@RegisterCaseStyle
public class SnakeCaseStyle implements CaseStyle {

  private static final char UNDERSCORE = '_';

  /**
   * Checks if the given name matches the snake_case style.
   *
   * @param name the name to check
   * @return {@code true} if the name is in snake_case, {@code false} otherwise
   */
  @Override
  public boolean matches(@NonNull final String name) {
    return this.isSnakeCase(name);
  }

  @Override
  public String getName() {
    return "snake_case";
  }

  @Override
  public Set<String> getVariantNames() {
    return Set.of(this.getName());
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
  private boolean isSnakeCase(
      @NonNull
          final String
              name) { // TODO: consider creating an annotation @NonEmpty to indicate its handled
    // upfront
    // Check if the name is all lowercase and contains underscores
    boolean hasUnderscore = false;
    for (int i = 0; i < name.length(); i++) {
      final char c = name.charAt(i);
      if (c == UNDERSCORE) {
        hasUnderscore = true;
      } else if (!isLowerCase(c)) {
        return false;
      }
    }
    return hasUnderscore;
  }
}
