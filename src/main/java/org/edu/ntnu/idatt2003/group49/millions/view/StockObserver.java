package org.edu.ntnu.idatt2003.group49.millions.view;

import java.math.BigDecimal;
import java.util.List;

public interface StockObserver {
  void update(List<BigDecimal> stockPrices, int week);
}
