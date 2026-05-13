package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;

import java.math.BigDecimal;
import java.util.Objects;

public class SaleAllocation {

  private final Share share;
  private final BigDecimal quantity;

  public SaleAllocation(Share share, BigDecimal quantity) {
    this.share = Objects.requireNonNull(share, "share cannot be null");
    this.quantity = Objects.requireNonNull(quantity, "quantity cannot be null");

    if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("quantity must be greater than zero");
    }

    if (quantity.compareTo(share.getQuantity()) > 0) {
      throw new IllegalArgumentException("quantity cannot be greater than share quantity");
    }
  }

  public Share getShare() {
    return share;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }
}
