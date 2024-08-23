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

import static java.util.Collections.unmodifiableSet;
import static java.util.Set.of;

import jakarta.annotation.Nullable;
import java.util.Set;
import lombok.NonNull;

/**
 * Represents a naming convention (case style) that can be matched against a given string.
 *
 * @implNote Implementations should define specific case styles such as camelCase, snake_case, etc.
 *     Each case style must have a unique name and may have variant names.
 * @author Sebastien Vermeille
 */
public interface CaseStyle {

  /**
   * Determines if the given string matches this case style.
   *
   * @param name the string to be checked, must not be null
   * @return true if the string matches this case style, false otherwise
   */
  boolean matches(@NonNull String name);

  /**
   * Returns the unique display name for this case style (e.g., "camelCase").
   *
   * @return the display name of the case style
   */
  String getName();

  /**
   * Returns a set of variant names for the case style (e.g., "PascalCase" and "UpperCamelCase"). By
   * default, only the primary name is returned.
   *
   * @return an immutable set of variant names
   */
  default Set<String> getVariantNames() {
    return unmodifiableSet(of(getName()));
  }

  boolean equals(@Nullable Object otherCaseStyle);
}
