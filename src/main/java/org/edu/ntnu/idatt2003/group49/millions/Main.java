package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
  static void main(String[] args) {
    List<Stock> stocks;
    final Stock nvidiaStock = new Stock("NVDA", "Nvidia", new BigDecimal("191.27"));
    final Stock appleStock = new Stock("AAPL", "Apple", new BigDecimal("276.43"));
    final Stock microsoftStock = new Stock("MSFT", "Microsoft", new BigDecimal("404.68"));
    final Stock amazonStock = new Stock("AMZN", "Amazon", new BigDecimal("204.62"));
    final Stock googleStockA = new Stock("GOOGL", "Alphabet Inc. (Class A)", new BigDecimal("311.20"));
    final Stock googleStockB = new Stock("GOOG", "Alphabet Inc. (Class C)", new BigDecimal("311.62"));

    stocks = new ArrayList<>();
    stocks.add(nvidiaStock);
    stocks.add(appleStock);
    stocks.add(microsoftStock);
    stocks.add(amazonStock);
    stocks.add(googleStockA);
    stocks.add(googleStockB);

    Random rand = new Random();

    Exchange nasdaq = new Exchange("Nasdaq", stocks);
    for(Stock stock : stocks) {
      System.out.println(stock.getSalesPrice());
    }
    nasdaq.advance();
    for(Stock stock : stocks) {
      System.out.println(stock.getSalesPrice());
    }
    System.out.println(rand.nextInt(2));
  }
}
