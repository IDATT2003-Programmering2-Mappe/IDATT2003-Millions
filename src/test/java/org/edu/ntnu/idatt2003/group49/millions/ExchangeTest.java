package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.model.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.Player;
import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
  void constructor_ThrowsWhenStocksIsNull() {
    assertThrows(NullPointerException.class, () -> new Exchange("Nasdaq", null));
  }

  @Test
  void constructor_ThrowsWhenStocksIsEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new Exchange("Nasdaq", new ArrayList<>()));
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
  void buy_ThrowsWhenSymbolIsNull() {
    assertThrows(NullPointerException.class,
        () -> exchange.buy(null, new BigDecimal("100"), new Player("Steve", new BigDecimal("1000"))));
  }

  @Test
  void buy_ThrowsWhenQuantityIsNull() {
    assertThrows(NullPointerException.class,
        () -> exchange.buy("NVDA", null, new Player("Steve", new BigDecimal("1000"))));
  }

  @Test
  void buy_ThrowsWhenPlayerIsNull() {
    assertThrows(NullPointerException.class, () -> exchange.buy("NVDA", new BigDecimal("100"), null));
  }

  @Test
  void buy_ThrowsWhenQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class,
        () -> exchange.buy("NVDA", new BigDecimal("-100"), new Player("Steve", new BigDecimal("1000"))));
  }

  @Test
  void buy_ThrowsWhenQuantityIsZero() {
    assertThrows(IllegalArgumentException.class,
        () -> exchange.buy("NVDA", new BigDecimal("0"), new Player("Steve", new BigDecimal("1000"))));
  }

  @Test
  void buy_ThrowsWhenStockDoesNotExistWithinExchange() {
    assertThrows(IllegalArgumentException.class,
        () -> exchange.buy("NTNU", new BigDecimal("100"), new Player("Steve", new BigDecimal("1000"))));
  }

  @Test
  void buy_CommitsPurchaseToPlayer() {
    Transaction purchase = exchange
        .buy("NVDA", new BigDecimal("1"), new Player("Steve", new BigDecimal("1000")));

    assertTrue(purchase.isCommited());
  }

  @Test
  void sell_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class,
        () -> exchange.sell(null, new Player("Steve", new BigDecimal("1000"))));
  }

  @Test
  void sell_ThrowsWhenPlayerIsNull() {
    assertThrows(NullPointerException.class,
        () -> exchange.sell(new Share(nvidiaStock, new BigDecimal("1"), nvidiaStock.getSalesPrice()), null));
  }

  @Test
  void sell_ThrowsWhenPlayerDoesNotOwnShare() {
    Player player = new Player("Steve", new BigDecimal("1000"));
    Share share = new Share(nvidiaStock, new BigDecimal("1"), nvidiaStock.getSalesPrice());

    assertThrows(IllegalStateException.class, () -> exchange.sell(share, player));
  }

  @Test
  void sell_CommitsSaleToPlayer() {
    Player player = new Player("Steve", new BigDecimal("1000"));
    Share share = new Share(nvidiaStock, new BigDecimal("1"), nvidiaStock.getSalesPrice());

    player.getPortfolio().addShare(share);

    Transaction sale = exchange.sell(share, player);
    assertTrue(sale.isCommited());
  }

  @Test
  void advance_IncrementsWeekByOne() {
    int weekBefore = exchange.getWeek();

    exchange.advance();

    int weekAfter = exchange.getWeek();
    assertEquals(weekBefore + 1, weekAfter);
  }

  @Test
  void advance_UpdatesPricesOfEachStock() {
    Map<String, Stock> beforeStockMap = exchange.getStockMap();

    exchange.advance();

    Map<String, Stock> afterStockMap = exchange.getStockMap();

    assertFalse(afterStockMap.keySet().stream()
        .allMatch(key -> beforeStockMap.get(key).getSalesPrice().compareTo(afterStockMap.get(key).getSalesPrice()) == 0));
  }
}