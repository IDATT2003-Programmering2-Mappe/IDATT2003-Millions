package org.edu.ntnu.idatt2003.group49.millions.view;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.util.Map;

public interface StockObserver {
  void update(Map<String, Stock> stockMap, int week);
}
