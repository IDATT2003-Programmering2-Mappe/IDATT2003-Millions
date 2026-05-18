package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.PurchaseRequest;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.CSVWriter;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ExchangeController {
  static Logger logger = Logger.getLogger(ExchangeController.class.getName());

  private final Exchange exchange;

  public ExchangeController(Exchange exchange) {
    this.exchange = exchange;
  }

  public void advance() {
    exchange.advance();
    logger.info("Advanced to week " + exchange.getWeek());

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

  public void buy(PurchaseRequest request) {
    exchange.buy(request.symbol(), request.quantity(), request.player());
  }

  public int getWeek() {
    return exchange.getWeek();
  }
}
