package org.edu.ntnu.idatt2003.group49.millions;

import java.math.BigDecimal;
import java.util.Objects;

public class  Share {
  private Stock stock;
  private BigDecimal quantity;
  private BigDecimal purchasePrice;

  public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
    if (quantity.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }
    if (purchasePrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Purchase price must be greater than zero");
    }
    this.stock = Objects.requireNonNull(stock, "stock cannot be null");
    this.quantity = Objects.requireNonNull(quantity, "quantity cannot be null");
    this.purchasePrice = Objects.requireNonNull(purchasePrice, "quantity cannot be null");
  }

  public Stock getStock() {
    return stock;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }
}
