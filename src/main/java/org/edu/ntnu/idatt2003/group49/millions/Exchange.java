package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.model.Player;
import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
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
    String lowerCaseSearch = searchTerm.toLowerCase();

    return stockMap.values().stream().filter(stock ->
            stock.getSymbol().contains(lowerCaseSearch)
                    || stock.getCompany().contains(lowerCaseSearch)).toList();
  }

  public Transaction buy(String symbol, BigDecimal quantity, Player player) {
    return null;
  }

  public Transaction sell(Share share, Player player) {
      if (!player.getPortfolio().contains(share)) {
        throw new IllegalArgumentException("player cannot sell a share they do not own.");
      }
      return null;
  }

  public void advance() {
    week++;
    stockMap.forEach((symbol, stock) -> {stock.addNewSalesPrice(stock.getSalesPrice());});
  }
}
