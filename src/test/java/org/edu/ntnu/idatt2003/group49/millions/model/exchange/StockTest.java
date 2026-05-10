package org.edu.ntnu.idatt2003.group49.millions.model.exchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

  Stock stock;

  @BeforeEach
  void setUp() {
    stock = new Stock("AAPL", "Apple", new BigDecimal("263.88"));
  }

  @Test
  void constructor_ThrowsWhenSymbolIsNull() {
    assertThrows(NullPointerException.class, () -> new Stock(null, "Apple", new BigDecimal("263.88")));
  }

  @Test
  void constructor_ThrowsWhenSymbolIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> new Stock("", "Apple", new BigDecimal("263.88")));
  }

  @Test
  void constructor_ThrowsWhenCompanyIsNull() {
    assertThrows(NullPointerException.class, () -> new Stock("AAPL", null, new BigDecimal("263.88")));
  }

  @Test
  void constructor_ThrowsWhenCompanyIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> new Stock("AAPL", "", new BigDecimal("263.88")));
  }

  @Test
  void constructor_ThrowsWhenSalesPriceIsNull() {
    assertThrows(NullPointerException.class, () -> new Stock("AAPL", "Apple", null));
  }

  @Test
  void addNewSalesPrice() {
    BigDecimal newSalesPrice = new BigDecimal("500");
    stock.addNewSalesPrice(newSalesPrice);
    assertEquals(newSalesPrice, stock.getSalesPrice());
  }

  @Test
  void addNewSalesPrice_ThrowsWhenSalesPriceIsNull() {
    assertThrows(NullPointerException.class, () -> stock.addNewSalesPrice(null));
  }

  @Test
  void addNewSalesPrice_ThrowsWhenSalesPriceIsNegative() {
    BigDecimal newSalesPrice = new BigDecimal("-500");
    assertThrows(IllegalArgumentException.class, () -> stock.addNewSalesPrice(newSalesPrice));
  }

  @Test
  void getHistoricalPrices_ReturnsCorrectList() {

    stock.addNewSalesPrice(new BigDecimal("280.54"));
    stock.addNewSalesPrice(new BigDecimal("270.67"));

    List<BigDecimal> historicalPrices = stock.getHistoricalPrices();

    assertEquals(List.of(
            new BigDecimal("263.88"),
            new BigDecimal("280.54"),
            new BigDecimal("270.67")),
            historicalPrices
    );
  }

  @Test
  void getHighestPrice_returnsHighestPrice() {

    stock.addNewSalesPrice(new BigDecimal("280.67"));
    stock.addNewSalesPrice(new BigDecimal("270.54"));

    BigDecimal highestPrice = stock.getHighestPrice();

    assertEquals(new BigDecimal("280.67"), highestPrice);
  }

  @Test
  void getLowestPrice_returnsLowestPrice() {

    stock.addNewSalesPrice(new BigDecimal("250.54"));
    stock.addNewSalesPrice(new BigDecimal("270.67"));

    BigDecimal lowestPrice = stock.getLowestPrice();

    assertEquals(new BigDecimal("250.54"), lowestPrice);
  }

  @Test
  void getLatestPriceChange_returnsDifferenceWhenPriceIncreases() {

    stock.addNewSalesPrice(new BigDecimal("250.00"));
    stock.addNewSalesPrice(new BigDecimal("270.00"));

    BigDecimal priceChange = stock.getLatestPriceChange();

    assertEquals(new BigDecimal("20.00"),priceChange);
  }

  @Test
  void getLatestPrice_returnsDifferenceWhenPriceDrops() {

    stock.addNewSalesPrice(new BigDecimal("270.00"));
    stock.addNewSalesPrice(new BigDecimal("250.00"));

    BigDecimal priceChange = stock.getLatestPriceChange();

    assertEquals(new BigDecimal("-20.00"), priceChange );
  }

  @Test
  void getLatestPrice_returnsZeroWhenOnlyOnePriceExists() {

    BigDecimal priceChange = stock.getLatestPriceChange();

    assertEquals(BigDecimal.ZERO, priceChange);
  }
}