package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MillionsFileWriter {
  private final static Logger logger = Logger.getLogger(MillionsFileWriter.class.getName());

  /**
   * Writes Stock Data to a file with the format: [symbol, company, price].
   *
   * @param path filePath.
   * @param stockData stockData to be written to file.
   * @throws IOException if writer is unable to write to file.
   */
  public static void writeStockDataToFile(Path path, Map<String, Stock> stockData) throws IOException {
    Objects.requireNonNull(path, "path is null");
    Objects.requireNonNull(stockData, "stockData is null");

    try (Writer writer = Files.newBufferedWriter(path)) {
      writer.write("# Stock Data" + System.lineSeparator());
      writer.write(System.lineSeparator());

      writer.write("symbol, company, price" + System.lineSeparator());
      stockData.forEach((symbol, stock) ->
      {
          try {
              writer.append("%s, %s, %s".formatted(stock.getSymbol(), stock.getCompany(), stock.getSalesPrice()));
              writer.append(System.lineSeparator());
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not write to file: ", e);
    }
  }
}
