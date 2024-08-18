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
package dev.cookiecode.stylesniffer.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import dev.cookiecode.stylesniffer.annotation.processor.RegisterCaseStyleAnnotationProcessor;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used by {@link RegisterCaseStyleAnnotationProcessor} to automatically populate the
 * generated CaseStyleIngestor class with all CaseStyle implementations.
 *
 * <p>This annotation should be placed on any class implementing a custom case style. The annotation
 * processor will detect these classes and include them in the generated CaseStyleIngestor for
 * runtime use.
 *
 * <p>Example usage: {@code @RegisterCaseStyle} public class MyCustomCaseStyle implements CaseStyle
 * { // implementation details }
 *
 * @author Sebastien Vermeille
 */
@Retention(SOURCE)
@Target(TYPE)
public @interface RegisterCaseStyle {}
