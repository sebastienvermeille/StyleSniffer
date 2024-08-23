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

import dev.cookiecode.stylesniffer.api.CaseStyle;
import jakarta.annotation.Nullable;
import java.util.*;

/**
 * Interface for a service that provides functionality for sniffing and managing case styles.
 *
 * <p>This interface allows for querying case styles by name, retrieving supported case style names,
 * and accessing case style names including their variants.
 *
 * <p>The implementation of this interface should handle the registration and retrieval of case
 * styles as well as manage the variations of case styles.
 *
 * @author Sebastien Vermeille
 */
public interface StyleSniffer {

  /**
   * Retrieves a {@link CaseStyle} that matches the given name.
   *
   * <p>This method searches through the registered case styles and returns the first style that
   * matches the provided name.
   *
   * @param name the name to match against case styles
   * @return an {@link Optional} containing the matching {@code CaseStyle} if found, or an empty
   *     {@code Optional} if no match is found
   */
  Optional<CaseStyle> getCaseStyle(@Nullable final String name);

  /**
   * Retrieves a {@link CaseStyle} based on either its variant name or its primary name.
   *
   * @param variantOrName the variant or primary name to match against case styles
   * @return an {@link Optional} containing the matching {@code CaseStyle} if found, or an empty
   *     {@code Optional} if no match is found
   */
  Optional<CaseStyle> getCaseStyleWithVariantOrName(@Nullable final String variantOrName);

  /**
   * Returns a set of names for all supported case styles.
   *
   * <p>This method provides a set of unique names for the case styles currently recognized by this
   * {@code StyleSniffer}.
   *
   * @return a {@link Set} of names for supported case styles
   */
  Set<String> getSupportedCaseStyles();

  /**
   * Returns a set of names for all supported case styles, including all variants.
   *
   * <p>This method provides a comprehensive list of all case style names recognized by this {@code
   * StyleSniffer}, including both primary names and their variants.
   *
   * @return a {@link Set} of names for all supported case styles, including variants
   */
  Set<String> getSupportedCaseStylesIncludingVariants();
}
