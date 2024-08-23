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
package dev.cookiecode.stylesniffer.impl.casestyle;

import static java.lang.Character.isUpperCase;

import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;
import dev.cookiecode.stylesniffer.api.BaseCaseStyle;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.Set;
import lombok.NonNull;

/**
 * {@link CaseStyle} implementation that checks for PascalCase naming style.
 *
 * <p>PascalCase style is characterized by the first character being uppercase, and each subsequent
 * word also starting with an uppercase letter, without any underscores or spaces.
 *
 * @author Sebastien Vermeille
 */
@RegisterCaseStyle
public class PascalCaseStyle extends BaseCaseStyle {

  /**
   * Checks if the given name matches the PascalCase style.
   *
   * @param name the name to check
   * @return {@code true} if the name is in PascalCase, {@code false} otherwise
   */
  @Override
  public boolean matches(@NonNull final String name) {
    return this.isPascalCase(name);
  }

  @Override
  public String getName() {
    return "PascalCase";
  }

  @Override
  public Set<String> getVariantNames() {
    return Set.of(this.getName(), "UpperCamelCase", "CamelCase");
  }

  private Set<Character> invalidCharacters() {
    return Set.of('_', ' ', '-', '*', ',', '\"', '\'', '#', '$', '@');
  }

  /**
   * Determines if the given name is in PascalCase style.
   *
   * <p>Checks if the first character is uppercase, there are no underscores or spaces, and the
   * naming follows the PascalCase convention where every word starts with an uppercase letter. The
   * method also ensures that sequences of uppercase letters are allowed if preceded by a lowercase
   * letter.
   *
   * @param name the name to check
   * @return {@code true} if the name is in PascalCase, {@code false} otherwise
   */
  private boolean isPascalCase(@NonNull final String name) {
    return !containsInvalidCharacters(name)
        && startsWithUpperCase(name)
        && followsPascalCasePattern(name);
  }

  private boolean startsWithUpperCase(@NonNull final String name) {
    return isUpperCase(name.charAt(0));
  }

  private boolean followsPascalCasePattern(@NonNull final String name) {
    boolean previousIsUpperCase = false;

    for (int i = 1; i < name.length(); i++) {
      char currentChar = name.charAt(i);

      if (isUpperCase(currentChar)) {
        if (previousIsUpperCase) {
          continue;
        }
        previousIsUpperCase = true;
      } else {
        previousIsUpperCase = false;
      }
    }
    return true;
  }

  private boolean containsInvalidCharacters(@NonNull String name) {
    return name.chars().anyMatch(c -> invalidCharacters().contains((char) c));
  }
}
