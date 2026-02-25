package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.transaction.Transaction;

import java.math.BigDecimal;
import java.util.*;

public class Exchange {
  private final String name;
  private int week;
  private final Map<String, Stock> stockMap;
  private Random random;

  public Exchange(String name, List<Stock> stocks) {
    this.name = name;
    this.week = 1;
    this.stockMap = new HashMap<>();
    stocks.forEach(stock -> stockMap.put(stock.getSymbol(), stock));
  }

  public String getName() {
    return name;
  }

  public int getWeek() {
    return week;
  }

  public boolean hasStock(String symbol) {
    return stockMap.containsKey(symbol);
  }

  public Stock getStock(String symbol) {
    return stockMap.get(symbol);
  }

  public List<Stock> findStocks(String searchTerm) {
    List<Stock> stocks = new ArrayList<>();


  }

  public Transaction buy(String symbol, BigDecimal quantity, Player player) {

  }

  public Transaction sell(Share share, Player player) {

  }

  public void advance() {
    week++;
    stockMap.forEach((symbol, stock) -> {stock.addNewSalesPrice(stock.getSalesPrice());});
  }
}
