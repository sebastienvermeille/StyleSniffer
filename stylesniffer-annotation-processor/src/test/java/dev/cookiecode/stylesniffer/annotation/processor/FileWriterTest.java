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

import static dev.cookiecode.stylesniffer.annotation.processor.FileWriter.DOT;
import static dev.cookiecode.stylesniffer.annotation.processor.RegisterCaseStyleAnnotationProcessor.GENERATED_CLASS_NAME;
import static dev.cookiecode.stylesniffer.annotation.processor.RegisterCaseStyleAnnotationProcessor.GENERATED_CLASS_PACKAGE_NAME;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class
 *
 * @author Sebastien Vermeille
 */
@ExtendWith(MockitoExtension.class)
class FileWriterTest {

  @Mock private ProcessingEnvironment processingEnvironment;

  @InjectMocks private FileWriter fileWriter;

  @Test
  void writeToFileShouldThrowAnIOExceptionGivenThereIsAnIssue() throws Exception {
    // GIVEN
    final var filerMock = mock(Filer.class);
    doThrow(new IOException("IO error")).when(filerMock).createSourceFile(anyString());
    when(processingEnvironment.getFiler()).thenReturn(filerMock);

    final var someValidCode = "some code that is not generating any error.";

    assertThrows(
        IOException.class,
        () -> {
          // WHEN
          fileWriter.writeToFile(someValidCode);
        });
  }

  @Test
  void writeToFileShouldPassGeneratedCodeToWriteMethodGivenThereIsNoIssue() throws Exception {
    // GIVEN
    final var filerMock = mock(Filer.class);
    when(processingEnvironment.getFiler()).thenReturn(filerMock);

    final var javaFileObjectMock = mock(JavaFileObject.class);
    doReturn(javaFileObjectMock)
        .when(filerMock)
        .createSourceFile(GENERATED_CLASS_PACKAGE_NAME + DOT + GENERATED_CLASS_NAME);

    final var writerMock = mock(Writer.class);
    when(javaFileObjectMock.openWriter()).thenReturn(writerMock);

    final var someValidCode = "some code that is not generating any error.";

    // WHEN
    fileWriter.writeToFile(someValidCode);

    // THEN
    verify(writerMock, times(1)).write(someValidCode);
  }
}
