package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MillionsFileReader {

  /*
   * Read lines from a file with a BufferedReader.
   */
  public static List<String[]> readFromCSVfile(Path path) {
    List<String[]> data = new ArrayList<>();
    try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        // do stuff, e.g. print to System.out
        if (line.startsWith("#") || line.isEmpty()) {
          continue;
        }
        String[] stocksArray = line.split(",");
        data.add(stocksArray);
      }
    } catch (IOException e) {
      // handle exception
    }
    return data;
  }

  public static List<Stock> readStocksFromCSVFile(Path path) {
    List<String[]> fileData = readFromCSVfile(path);
    List<Stock> stocks = new ArrayList<>();
    fileData.removeFirst();

    for (String[] stocksArray : fileData) {
      String symbol = stocksArray[0];
      String company = stocksArray[1];
      String price = stocksArray[2];

      stocks.add(new Stock(symbol, company, new BigDecimal(price)));
    }
    return stocks;
  }
}
