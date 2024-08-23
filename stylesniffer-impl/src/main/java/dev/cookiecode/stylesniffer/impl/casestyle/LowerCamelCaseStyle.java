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

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.util.Set.of;

import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;
import dev.cookiecode.stylesniffer.api.BaseCaseStyle;
import dev.cookiecode.stylesniffer.api.CaseStyle;
import java.util.Set;
import lombok.NonNull;

/**
 * {@link CaseStyle} implementation that checks for camelCase (LowerCamelCase) naming style.
 *
 * <p>camelCase style is characterized by the first character being lowercase, and each subsequent
 * word starting with an uppercase letter, without any underscores or spaces.
 *
 * @author Sebastien Vermeille
 */
@RegisterCaseStyle
public class LowerCamelCaseStyle extends BaseCaseStyle {

  /**
   * Checks if the given name matches the CamelCase style.
   *
   * @param name the name to check
   * @return {@code true} if the name is in CamelCase, {@code false} otherwise
   */
  @Override
  public boolean matches(@NonNull final String name) {
    return this.isLowerCamelCase(name);
  }

  @Override
  public String getName() {
    return "camelCase";
  }

  @Override
  public Set<String> getVariantNames() {
    return of(this.getName(), "LowerCamelCase");
  }

  /**
   * Determines if the given name is in CamelCase style.
   *
   * <p>Checks if the first character is lowercase and each subsequent word starts with an uppercase
   * letter, with no underscores or spaces.
   *
   * @param name the name to check
   * @return {@code true} if the name is in CamelCase, {@code false} otherwise
   */
  private boolean isLowerCamelCase(@NonNull final String name) {
    if (name.isEmpty()) {
      return false;
    }

    // The first character must be lowercase
    if (!isLowerCase(name.charAt(0))) {
      return false;
    }

    // Ensure the rest of the string follows the LowerCamelCase rules
    boolean hasUpperCase = false;
    for (int i = 1; i < name.length(); i++) {
      final char c = name.charAt(i);

      if (isUpperCase(c)) {
        // An uppercase letter should follow a lowercase letter
        if (hasUpperCase) {
          // Found consecutive uppercase letters which are not allowed in LowerCamelCase
          return false;
        }
        hasUpperCase = true;
      } else if (isLowerCase(c)) {
        // Reset the uppercase flag when a lowercase letter is found
        hasUpperCase = false;
      } else {
        // Characters other than letters are not allowed
        return false;
      }
    }

    return true;
  }
}
