package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ExchangeController {
  private final Exchange exchange;

  public ExchangeController(Exchange exchange) {
    this.exchange = exchange;
  }

  public void advance() throws IOException {
    System.out.println("Advance clicked");
    exchange.advance();
  }

  public Map<String, Stock> getStockMap() {
    return exchange.getStockMap();
  }

  public List<BigDecimal> getStockPrices(String symbol) {
    return exchange.getStock(symbol).getHistoricalPrices();
  }
}
