package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.CSVWriter;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ExchangeController {
  private final Exchange exchange;

  public ExchangeController(Exchange exchange) {
    this.exchange = exchange;
  }

  public void advance() {
    System.out.println("Advance clicked");
    exchange.advance();

    CSVWriter.appendStockPricesToFile(Path.of("data/stock_data.csv"), getStockMap());
  }

  public Map<String, Stock> getStockMap() {
    return exchange.getStockMap();
  }

  public List<BigDecimal> getStockPrices(String symbol) {
    return exchange.getStock(symbol).getHistoricalPrices();
  }

  public Stock getStock(String symbol) {
    return exchange.getStock(symbol);
  }
}
