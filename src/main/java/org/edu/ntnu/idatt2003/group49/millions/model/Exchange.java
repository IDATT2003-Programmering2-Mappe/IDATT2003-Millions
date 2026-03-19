package org.edu.ntnu.idatt2003.group49.millions.model;

import org.edu.ntnu.idatt2003.group49.millions.model.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Purchase;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Sale;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Exchange {
  private final String name;
  private int week = 1;
  private final Map<String, Stock> stockMap;
  private final Random random;

  public Exchange(String name, List<Stock> stocks) {
    Objects.requireNonNull(stocks, "name must not be null");
    if (stocks.isEmpty()) {
      throw new IllegalArgumentException("stocks cannot be empty");
    }
    this.name = Objects.requireNonNull(name, "name cannot be null");
    this.stockMap = new HashMap<>();
    stocks.forEach(stock -> stockMap.put(stock.getSymbol(), stock));
    random = new Random();
  }

  public String getName() {
    return name;
  }

  public int getWeek() {
    return week;
  }

  public Map<String, Stock> getStockMap() {
    return stockMap;
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
    TransactionCalculator purchaseCalculator = new PurchaseCalculator(share);

    Purchase purchase = new Purchase(share, getWeek(), purchaseCalculator);
    purchase.commit(player);
    return purchase;
  }

  public Transaction sell(Share share, Player player) {
    Objects.requireNonNull(share, "share cannot be null");
    Objects.requireNonNull(player, "player cannot be null");
    if (!player.getPortfolio().contains(share)) {
      throw new IllegalStateException("player cannot sell a share they do not own.");
    }

    TransactionCalculator saleCalculator = new SaleCalculator(share);

    Transaction sale = new Sale(share, getWeek(), saleCalculator);
    sale.commit(player);
    return sale;
  }

  public void advance() {
    double maxPercentage = 0.11;

    stockMap.forEach((symbol, stock) -> {
      int change = random.nextInt(2); // Returns 0 or 1; 0 being negative change and 1 being positive change
      if (change == 0) {
        stock.addNewSalesPrice(stock.getSalesPrice()
            .subtract(stock.getSalesPrice().multiply(BigDecimal.valueOf(random.nextDouble(maxPercentage))).setScale(2, RoundingMode.HALF_UP)));
      }
      else {
        stock.addNewSalesPrice(stock.getSalesPrice()
            .add(stock.getSalesPrice().multiply(BigDecimal.valueOf(random.nextDouble(maxPercentage))).setScale(2, RoundingMode.HALF_UP)));
      }
    });
    week++;
  }

  public List<Stock> getGainers(int limit) {
    if (limit < 0) {
      throw new IllegalArgumentException("limit cannot be negative");
    }

    List<Stock> gainerList = stockMap.values().stream()
            .filter(stock -> stock.getLatestPriceChange().compareTo(BigDecimal.ZERO) > 0)
            .sorted(Comparator.comparing(Stock::getLatestPriceChange).reversed())
            .limit(limit)
            .toList();

    if (limit > gainerList.size()) {
      throw new IllegalArgumentException("limit cannot be greater than gainerList size");
    }

    return gainerList;
  }

  public List<Stock> getLosers(int limit) {
    if (limit < 0) {
      throw new IllegalArgumentException("limit cannot be negative");
    }

    List<Stock> loserList = stockMap.values().stream()
            .filter(stock -> stock.getLatestPriceChange().compareTo(BigDecimal.ZERO) < 0)
            .sorted(Comparator.comparing(Stock::getLatestPriceChange))
            .limit(limit)
            .toList();

    if (limit > loserList.size()) {
      throw new IllegalArgumentException("limit cannot be greater than gainerList size");
    }

    return loserList;
  }

}
