package org.edu.ntnu.idatt2003.group49.millions.model;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.StockObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StockSubject {
  List<StockObserver> stockObservers;

  public StockSubject() {
    stockObservers = new ArrayList<>();
  }

  public void addObserver(StockObserver stockObserver) {
    stockObservers.add(stockObserver);
  };

  public void removeObserver(StockObserver stockObserver) {
    stockObservers.remove(stockObserver);
  };

  public void notifyObservers(Map<String, Stock> stockMap, int week) {
    stockObservers.forEach(observer -> observer.update(stockMap, week));
  };
}
