package org.edu.ntnu.idatt2003.group49.millions.model;

import org.edu.ntnu.idatt2003.group49.millions.view.StockObserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Test extends Subject {
  private int week = 1;
  private final List<BigDecimal> stockPrices;

  public Test() {
    this.stockPrices = new ArrayList<>();
    stockPrices.add(new BigDecimal("10"));
  }

  public void advance() {
    this.stockPrices.add(new BigDecimal("500"));
    System.out.println(this.stockPrices.size());
    notifyObservers(stockPrices, week);
    this.week++;
  }
}
