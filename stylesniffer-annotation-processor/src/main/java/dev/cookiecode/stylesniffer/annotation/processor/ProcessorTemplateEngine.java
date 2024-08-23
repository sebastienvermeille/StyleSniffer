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

import lombok.experimental.Delegate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Wrapper for Thymeleaf template engine pre-configured for the project
 *
 * @author Sebastien Vermeille
 * @see TemplateEngine
 */
public class ProcessorTemplateEngine {

  private static final String TEMPLATES_DIR = "templates/";
  private static final String TEMPLATE_EXTENSION = ".tpl";
  private static final String TEMPLATE_MODE = "TEXT";
  private static final String TEMPLATE_ENCODING = "UTF-8";

  @Delegate(types = TemplateEngine.class)
  private final TemplateEngine templateEngine = new TemplateEngine();

  public ProcessorTemplateEngine() {
    final var templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix(TEMPLATES_DIR);
    templateResolver.setSuffix(TEMPLATE_EXTENSION);
    templateResolver.setTemplateMode(TEMPLATE_MODE);
    templateResolver.setCharacterEncoding(TEMPLATE_ENCODING);

    templateEngine.setTemplateResolver(templateResolver);
  }
}
