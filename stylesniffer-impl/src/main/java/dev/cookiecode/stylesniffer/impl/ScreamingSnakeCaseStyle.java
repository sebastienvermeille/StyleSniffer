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

import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.Set;
import lombok.NonNull;

/**
 * {@link CaseStyle} implementation that checks for UPPER_SNAKE_CASE naming style.
 *
 * <p>UPPER_SNAKE_CASE style is characterized by all uppercase letters and underscores separating
 * words.
 *
 * @author Sebastien Vermeille
 */
@RegisterCaseStyle
public class ScreamingSnakeCaseStyle implements CaseStyle {

  private static final char UNDERSCORE = '_';

  @Override
  public boolean matches(@NonNull final String name) {
    return this.isScreamingSnakeCase(name);
  }

  @Override
  public String getName() {
    return "SCREAMING_SNAKE_CASE";
  }

  @Override
  public Set<String> getVariantNames() {
    return Set.of(this.getName());
  }

  /**
   * Determines if the given name is in UPPER_SNAKE_CASE style.
   *
   * <p>Checks if the name consists entirely of uppercase letters and underscores, with at least one
   * underscore present, and no other characters or lowercase letters.
   *
   * @param name the name to check
   * @return {@code true} if the name is in UPPER_SNAKE_CASE, {@code false} otherwise
   */
  private boolean isScreamingSnakeCase(@NonNull final String name) {
    if (name.isEmpty()) {
      return false;
    }

    boolean hasUnderscore = false;
    boolean lastWasUnderscore = false;

    // Check if the string starts or ends with an underscore
    if (name.charAt(0) == UNDERSCORE || name.charAt(name.length() - 1) == UNDERSCORE) {
      return false;
    }

    for (int i = 0; i < name.length(); i++) {
      final char c = name.charAt(i);

      if (c == UNDERSCORE) {
        hasUnderscore = true;
        if (lastWasUnderscore) {
          // Consecutive underscores are not allowed
          return false;
        }
        lastWasUnderscore = true;
      } else if (!Character.isUpperCase(c)) {
        // Non-uppercase characters are not allowed
        return false;
      } else {
        lastWasUnderscore = false;
      }
    }

    // At least one underscore must be present
    return hasUnderscore;
  }
}
