package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MillionsFileWriter {
  Logger logger = Logger.getLogger(MillionsFileWriter.class.getName());

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

  public static void writeStockDataToFile(Path path, Map<String, Stock> stockData) {
    stockData.forEach((symbol, stock) -> {
      try {
        writeToFileTryWithResource(path, symbol + ", " + stock.getCompany() + ", " + stock.getSalesPrice());

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
