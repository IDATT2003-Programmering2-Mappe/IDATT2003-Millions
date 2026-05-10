package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVWriter {
  private final static Logger logger = Logger.getLogger(CSVWriter.class.getName());

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
      writer.write("# Symbol,Company,Price" + System.lineSeparator());
      writer.write(System.lineSeparator());

      stockData.forEach((symbol, stock) ->
      {
        System.out.println(symbol);
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

  public static void appendCurrentStockPrices(Path path, Map<String, Stock> stockMap) {
    Objects.requireNonNull(path, "path is null");
    Objects.requireNonNull(stockMap, "stockData is null");
    List<Stock> stocks = new ArrayList<>(stockMap.values());

    try {
      List<String> lines = Files.readAllLines(path);

      for(int i = 0; i < lines.size(); i++) {
        if (lines.get(i).startsWith("#") || lines.get(i).isBlank()) {
          continue;
        }
        if (i >= stockMap.size()) {
          break;
        }

        if (lines.get(i - 3).startsWith(stocks.get(i).getSymbol())) {
          System.out.println("Hellodsianbdisjahb");
          lines.set(i, lines.get(i) + ", " + stocks.get(i).getSalesPrice());
        }
      }

      Files.write(path, lines);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
