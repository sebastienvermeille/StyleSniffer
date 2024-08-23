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

import static dev.cookiecode.stylesniffer.annotation.processor.RegisterCaseStyleAnnotationProcessor.GENERATED_CLASS_NAME;
import static dev.cookiecode.stylesniffer.annotation.processor.RegisterCaseStyleAnnotationProcessor.GENERATED_CLASS_PACKAGE_NAME;

import java.io.IOException;
import javax.annotation.processing.ProcessingEnvironment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Handles the writing of the generated source code to a file.
 *
 * <p>This class is responsible for creating a new Java source file and writing the generated code
 * into it.
 *
 * <p>The file is created in the package {@code dev.cookiecode.stylesniffer.generated}.
 *
 * @author Sebastien Vermeille
 */
@RequiredArgsConstructor
public class FileWriter {

  static final String DOT = ".";
  private final ProcessingEnvironment processingEnv;

  /**
   * Writes the generated code to a new Java source file.
   *
   * @param generatedCode The code to write into the file.
   * @throws IOException If an I/O error occurs while writing the file.
   */
  public void writeToFile(@NonNull String generatedCode) throws IOException {
    try (var writer =
        processingEnv
            .getFiler()
            .createSourceFile(GENERATED_CLASS_PACKAGE_NAME + DOT + GENERATED_CLASS_NAME)
            .openWriter()) {
      writer.write(generatedCode);
    }
  }
}
