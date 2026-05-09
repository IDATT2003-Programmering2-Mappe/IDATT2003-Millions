package org.edu.ntnu.idatt2003.group49.millions.model;

import org.edu.ntnu.idatt2003.group49.millions.view.StockObserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
  List<StockObserver> stockObservers;

  public Subject() {
    stockObservers = new ArrayList<>();
  }

  public void addObserver(StockObserver stockObserver) {
    stockObservers.add(stockObserver);
  };

  public void removeObserver(StockObserver stockObserver) {
    stockObservers.remove(stockObserver);
  };

  public void notifyObservers(List<BigDecimal> stockData, int week) {
    stockObservers.forEach(observer -> observer.update(stockData, week));
  };
}
