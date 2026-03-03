package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeTest {

  private Exchange exchange;
  private List<Stock> stocks;
  private final Stock nvidiaStock = new Stock("NVDA", "Nvidia", new BigDecimal("191.27"));
  private final Stock appleStock = new Stock("AAPL", "Apple", new BigDecimal("276.43"));
  private final Stock microsoftStock = new Stock("MSFT", "Microsoft", new BigDecimal("404.68"));
  private final Stock amazonStock = new Stock("AMZN", "Amazon", new BigDecimal("204.62"));
  private final Stock googleStockA = new Stock("GOOGL", "Alphabet Inc. (Class A)", new BigDecimal("311.20"));
  private final Stock googleStockB = new Stock("GOOG", "Alphabet Inc. (Class C)", new BigDecimal("311.62"));

  @BeforeEach
  void setUp() {
    stocks = new ArrayList<>();
    stocks.add(nvidiaStock);
    stocks.add(appleStock);
    stocks.add(microsoftStock);
    stocks.add(amazonStock);
    stocks.add(googleStockA);
    stocks.add(googleStockB);
    exchange = new Exchange("Nasdaq", stocks);
  }

  @Test
  void constructor_ThrowsWhenNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Exchange(null, stocks));
  }

  @Test
  void hasStock_ThrowsWhenSymbolIsNull() {
    assertThrows(NullPointerException.class, () -> exchange.hasStock(null));
  }

  @Test
  void hasStock_ReturnsTrueIfStockExists() {
    assertTrue(exchange.hasStock("NVDA"));
  }

  @Test
  void hasStock_ReturnsFalseIfStockDoesNotExists() {
    assertFalse(exchange.hasStock("NTNU"));
  }

  @Test
  void getStock_ThrowsWhenSymbolIsNull() {
    assertThrows(NullPointerException.class, () -> exchange.getStock(null));
  }

  @Test
  void getStock_GetsStockWithSymbol() {
    assertEquals(nvidiaStock, exchange.getStock("NVDA"));
  }

  @Test
  void findStocks_ThrowsWhenSearchTermIsNull() {
    assertThrows(NullPointerException.class, () -> exchange.findStocks(null));
  }

  @Test
  void findStocks_findsAllStocksMatchingSearchTerm() {
    String searchTerm = "GO";

    List<Stock> stocks = exchange.findStocks(searchTerm);

    assertAll(
        () -> assertFalse(stocks.isEmpty()),
        () -> assertTrue(stocks.stream()
            .allMatch(stock ->
                stock.getSymbol().contains(searchTerm) ||
                stock.getCompany().contains(searchTerm))),
        () -> assertTrue(stocks.contains(googleStockA)),
        () -> assertTrue(stocks.contains(googleStockB))
    );
  }

  @Test
  void buy() {
  }

  @Test
  void sell() {
  }

  @Test
  void advance() {
  }
}