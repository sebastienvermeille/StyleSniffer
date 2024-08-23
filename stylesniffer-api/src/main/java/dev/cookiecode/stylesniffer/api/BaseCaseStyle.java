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
    if(obj == null){
      return false;
    }
    if(obj instanceof CaseStyle caseStyle){
      return equals(caseStyle);
    } else {
      return false;
    }
  }

  /**
   * Determines equality based on the {@link #getName()} method.
   *
   * @param otherCaseStyle the object to compare with
   * @return true if the given object is a {@code CaseStyle} with the same name, false otherwise
   */
  public boolean equals(@Nullable final CaseStyle otherCaseStyle) {
    if (this == otherCaseStyle) {
      return true;
    }
    if (otherCaseStyle == null || getClass() != otherCaseStyle.getClass()) {
      return false;
    }
    return getName().equals(otherCaseStyle.getName());
  }

  /**
   * Returns a hash code based on the {@link #getName()} method.
   *
   * @return the hash code of the case style's name
   */
  public int hashCode(@Nullable CaseStyle style) {
    return style != null ? style.getName().hashCode() : 0;
  }
}
