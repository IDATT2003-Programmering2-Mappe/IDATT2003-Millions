package org.edu.ntnu.idatt2003.group49.millions.model.exchange;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ShareTest {
  @Test
  void constructor_ThrowsWhenStockIsNull() {
    assertThrows(NullPointerException.class, () -> {
      new Share(
          null,
          new BigDecimal("50"),
          new BigDecimal("263")
      );
    });
  }

  @Test
  void constructor_ThrowsWhenQuantityIsNull() {
    assertThrows(NullPointerException.class, () -> {
      new Share(
          new Stock("APPL", "Apple", new BigDecimal("100")),
          null,
          new BigDecimal("263")
      );
    });
  }

  @Test
  void constructor_ThrowsWhenQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Share(
          new Stock("APPL", "Apple", new BigDecimal("100")),
          new BigDecimal("-50"),
          new BigDecimal("263")
      );
    });
  }

  @Test
  void constructor_ThrowsWhenPurchasePriceIsNull() {
    assertThrows(NullPointerException.class, () -> {
      new Share(
          new Stock("APPL", "Apple", new BigDecimal("100")),
          new BigDecimal("50"),
          null
      );
    });
  }

  @Test
  void constructor_ThrowsWhenPurchasePriceIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Share(
          new Stock("APPL", "Apple", new BigDecimal("100")),
          new BigDecimal("50"),
          new BigDecimal("-250")
      );
    });
  }
}