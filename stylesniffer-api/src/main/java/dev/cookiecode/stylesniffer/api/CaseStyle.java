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

import static java.util.Set.of;

import java.util.Set;
import lombok.NonNull;

/**
 * Represents a naming convention (case style) that can be matched against a given string.
 *
 * @author Sebastien Vermeille
 */
public interface CaseStyle {

  boolean matches(@NonNull String name);

  /**
   * Returns the unique display name for this case style (e.g., "camelCase").
   *
   * @return the display name of the case style
   */
  String getName();

  /**
   * Returns a set of variant names for the case style (e.g., "PascalCase" and "UpperCamelCase").
   * Implementations should override this method to provide variant names if applicable.
   *
   * @return a set of variant names
   */
  default Set<String> getVariantNames() {
    return of(this.getName()); // By default, return only the primary name
  }
}
