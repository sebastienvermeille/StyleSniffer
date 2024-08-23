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
package dev.cookiecode.stylesniffer.annotation.processor;

import static java.util.stream.Collectors.toList;

import dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import lombok.NonNull;

/**
 * Collects elements annotated with {@link
 * dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle}.
 *
 * <p>This class is responsible for scanning the {@link RoundEnvironment} and extracting the fully
 * qualified class names of elements annotated with {@code @RegisterCaseStyle}.
 *
 * <p>The collected class names are used in the template rendering process.
 *
 * @author Sebastien Vermeille
 * @see dev.cookiecode.stylesniffer.annotation.RegisterCaseStyle
 */
public class CaseStyleElementsCollector {

  static final Class<? extends Annotation> ANNOTATION_CLASS = RegisterCaseStyle.class;

  public List<String> collectElements(@NonNull RoundEnvironment roundEnv) {
    return roundEnv.getElementsAnnotatedWith(ANNOTATION_CLASS).stream()
        .map(element -> ((TypeElement) element).getQualifiedName().toString())
        .toList();
  }
}
