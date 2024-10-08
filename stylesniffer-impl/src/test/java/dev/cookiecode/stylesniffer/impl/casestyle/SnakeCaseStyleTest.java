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

import dev.cookiecode.stylesniffer.testkit.CaseStyleTestKit;
import java.util.List;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
@SuppressWarnings(
    "java:S2187") // sonar is not able to detect that BaseCaseStyleTest interface generates test
class SnakeCaseStyleTest implements CaseStyleTestKit<SnakeCaseStyle> {

  @Override
  public SnakeCaseStyle createCaseStyle() {
    return new SnakeCaseStyle();
  }

  @Override
  public List<String> nonMatchingInputs() {
    return List.of("SomePascalCase", "someCamelCase", "some-kebab-case");
  }

  @Override
  public List<String> matchingInputs() {
    return List.of("snake_case_input", "another_input", "a_very_long_name_using_such_case_style");
  }
}
