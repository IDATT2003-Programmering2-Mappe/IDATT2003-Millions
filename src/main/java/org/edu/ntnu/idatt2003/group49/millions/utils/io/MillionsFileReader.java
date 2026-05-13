package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.nio.file.Path;
import java.util.List;

public interface MillionsFileReader {
  List<Stock> readStocks(Path path);
}


