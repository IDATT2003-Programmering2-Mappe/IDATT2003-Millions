package org.edu.ntnu.idatt2003.group49.millions.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
}