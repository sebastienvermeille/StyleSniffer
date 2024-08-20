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

import static java.util.List.of;

import dev.cookiecode.stylesniffer.testkit.BaseCaseStyleTest;
import java.util.List;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
@SuppressWarnings("java:S2187") // sonar is not able to detect that BaseCaseStyleTest interface generates test
class ScreamingSnakeCaseStyleTest implements BaseCaseStyleTest<ScreamingSnakeCaseStyle> {

  @Override
  public ScreamingSnakeCaseStyle createCaseStyle() {
    return new ScreamingSnakeCaseStyle();
  }

  @Override
  public List<String> nonMatchingInputs() {
    return of(
        "SomePascalCase",
        "someCamelCase",
        "some-kebab-case",
        "some_snake_case",
        "Screaming_Snake_Case",
        "SCREAMING__SNAKE_CASE",
        "_SCREAMING_SNAKE_CASE",
        "SCREAMING_SNAKE_CASE_");
  }

  @Override
  public List<String> matchingInputs() {
    return of("SCREAMING_WILL_NOT_MAKE_IT_BETTER", "SOME_VALUE");
  }
}
