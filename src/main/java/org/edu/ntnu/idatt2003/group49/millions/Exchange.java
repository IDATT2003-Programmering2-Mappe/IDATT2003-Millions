package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.Player;
import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.edu.ntnu.idatt2003.group49.millions.transaction.Purchase;
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
    if (symbol == null) {
      throw new IllegalArgumentException("symbol cannot be null");
    }
    return stockMap.containsKey(symbol);
  }

  public Stock getStock(String symbol) {
    if (symbol == null) {
      throw new NullPointerException("symbol cannot be null");
    }
    return stockMap.get(symbol);
  }

  public List<Stock> findStocks(String searchTerm) {
    if (searchTerm == null) {
      throw new NullPointerException("searchTerm cannot be null");
    }
    String lowerCaseSearch = searchTerm.toLowerCase();

    return stockMap.values().stream().filter(stock ->
        stock.getSymbol().toLowerCase().contains(lowerCaseSearch) ||
        stock.getCompany().toLowerCase().contains(lowerCaseSearch)).toList();
  }

  public Transaction buy(String symbol, BigDecimal quantity, Player player) {
    if (!hasStock(symbol)) {
      throw new IllegalArgumentException("Cannot purchase a share of a stock that does not exist within the Exchange");
    }
    Stock stock = getStock(symbol);
    Share share = new Share(stock, quantity, stock.getSalesPrice());
    Purchase purchase = new Purchase(share, getWeek(), new PurchaseCalculator(share));
    purchase.commit(player);
    return purchase;
  }

  public Transaction sell(Share share, Player player) {
    if (share == null ||  player == null) {
      throw new NullPointerException("share or player cannot be null");
    }
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
