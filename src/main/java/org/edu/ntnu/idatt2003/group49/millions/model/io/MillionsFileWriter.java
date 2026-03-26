package org.edu.ntnu.idatt2003.group49.millions.model.io;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
   */
  public static void writeStockDataToFile(Path path, Map<String, Stock> stockData) {
    Objects.requireNonNull(path, "path is null");
    Objects.requireNonNull(stockData, "stockData is null");

    try (Writer writer = Files.newBufferedWriter(
        path,
        StandardOpenOption.CREATE
    )) {
      writer.write("# Stock Data"
        + System.lineSeparator()
        + System.lineSeparator());

      writer.write("symbol, company, price" + System.lineSeparator());
      stockData.forEach((symbol, stock) ->
      {
        try {
          writer
              .append("%s, %s, %s".formatted(stock.getSymbol(), stock.getCompany(), stock.getSalesPrice()))
              .append(System.lineSeparator());
        } catch (IOException e) {
          logger.log(Level.SEVERE, "Could not append to file", e);
        }
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not write to file: ", e);
    }
  }
}
