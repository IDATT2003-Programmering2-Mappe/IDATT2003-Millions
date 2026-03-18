package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class MillionsFileWriter {

  /*
   * Write text to a file with a BufferedWriter.
   * Try-with-resource closes the stream automatically.
   */
  public static void writeToFileTryWithResource(Path path, String text) throws IOException {
    try (Writer writer = Files.newBufferedWriter(path)) {
      writer.write(text);
    } catch (IOException e) {
      throw new IOException("Could not write to file: " + path);
    }
  }

  public static void writeStockDataToFile() {

  }
}
