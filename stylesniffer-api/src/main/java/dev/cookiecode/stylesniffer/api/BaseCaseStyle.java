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

import jakarta.annotation.Nullable;

/**
 * Base implementation of the {@link CaseStyle} interface that provides default implementations for
 * {@code equals} and {@code hashCode} based on the case style's name.
 *
 * @author Sebastien Vermeille
 */
public abstract class BaseCaseStyle implements CaseStyle {

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    CaseStyle caseStyle = (CaseStyle) obj;
    return getName().equals(caseStyle.getName());
  }

  /**
   * Returns a hash code based on the {@link #getName()} method.
   *
   * @return the hash code of the case style's name
   */
  public int hashCode(@Nullable Object obj) {
    if (obj instanceof CaseStyle style) {
      return style.getName().hashCode();
    }
    return 0;
  }
}
