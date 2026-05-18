package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CSVMillionsFileReader implements MillionsFileReader {
  @Override
  public List<Stock> readStocks(Path path) {
    return CSVReader.convertCSVToStocksList(path);
  }

  @Override
  public void saveStockData(Map<String, Stock> stockMap) {
    CSVWriter.appendStockPricesToFile(Path.of("data/stock_data.csv"), stockMap);
  }
}
