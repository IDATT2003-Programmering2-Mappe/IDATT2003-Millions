package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MillionsFileReader {
  static Logger logger = Logger.getLogger(MillionsFileReader.class.getName());

  /*
   * Read lines from a file with a BufferedReader.
   */
  public static List<String[]> readFromCSVFile(Path path) throws IOException {
    List<String[]> data = new ArrayList<>();
    try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
      String line;
      int lineNumber = 0;

      while ((line = bufferedReader.readLine()) != null) {
        lineNumber++;

        line = line.trim();

        // do stuff, e.g. print to System.out
        if (line.startsWith("#") || line.isEmpty()) {
          continue;
        }

        String[] stocksArray = line.split(",");

        data.add(stocksArray);
      }
    } catch (IOException e) {
      // handle exception
      throw new IOException("Could not read from CSV file: " + path);
    }

    return data;
  }

  public static List<Stock> convertCSVFileToStocks(Path path) throws IOException {
    List<String[]> data = readFromCSVFile(path);
    List<Stock> stocks = new ArrayList<>();
    for (String[] stocksArray : data) {
      String symbol = stocksArray[0];
      String company = stocksArray[1];
      String price = stocksArray[2];

      stocks.add(new Stock(symbol, company, new BigDecimal(price)));
    }
    return stocks;
  }
}
