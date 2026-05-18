package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface MillionsFileReader {
  List<Stock> readStocks(Path path);
  void saveStockData(Map<String, Stock> stockMap);
}


