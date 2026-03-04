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
  private int week = 1;
  private final Map<String, Stock> stockMap;
  private Random random;

  public Exchange(String name, List<Stock> stocks) {
    Objects.requireNonNull(stocks, "name must not be null");
    if (stocks.isEmpty()) {
      throw new IllegalArgumentException("stocks cannot be empty");
    }
    this.name = Objects.requireNonNull(name, "name cannot be null");
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
    Objects.requireNonNull(symbol, "symbol cannot be null");
    return stockMap.containsKey(symbol);
  }

  public Stock getStock(String symbol) {
    Objects.requireNonNull(symbol, "symbol cannot be null");
    return stockMap.get(symbol);
  }

  public List<Stock> findStocks(String searchTerm) {
    Objects.requireNonNull(searchTerm, "searchTerm cannot be null");
    String lowerCaseSearch = searchTerm.toLowerCase();

    return stockMap.values().stream().filter(stock ->
        stock.getSymbol().toLowerCase().contains(lowerCaseSearch) ||
        stock.getCompany().toLowerCase().contains(lowerCaseSearch)).toList();
  }

  public Transaction buy(String symbol, BigDecimal quantity, Player player) {
    Objects.requireNonNull(symbol, "symbol cannot be null");
    Objects.requireNonNull(quantity, "quantity cannot be null");
    Objects.requireNonNull(player, "player cannot be null");
    if (!hasStock(symbol)) {
      throw new IllegalArgumentException("Cannot buy a share of '" + symbol + "' because it does not exist within the exchange");
    }
    if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Cannot buy a share of '" + symbol + "' because 'quantity' is negative or zero");
    }

    Stock stock = getStock(symbol);
    Share share = new Share(stock, quantity, stock.getSalesPrice());
    PurchaseCalculator calculator = new PurchaseCalculator(share);

    Purchase purchase = new Purchase(share, getWeek(), calculator);
    purchase.commit(player);
    return purchase;
  }

  public Transaction sell(Share share, Player player) {
    Objects.requireNonNull(share, "share cannot be null");
    Objects.requireNonNull(player, "player cannot be null");
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
