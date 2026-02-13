package org.edu.ntnu.idatt2003.group49.millions.calculator;

import org.edu.ntnu.idatt2003.group49.millions.Share;

import java.math.BigDecimal;

public class SaleCalculator implements TransactionCalculator {
  private BigDecimal purchasePrice;
  private BigDecimal salesPrice;
  private BigDecimal quantity;

  public SaleCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice    = share.getStock().getSalesPrice();
    this.quantity      = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return null;
  }

  @Override
  public BigDecimal calculateCommission() {
    return null;
  }

  @Override
  public BigDecimal calculateTax() {
    return null;
  }

  @Override
  public BigDecimal calculateTotal() {
    return null;
  }
}
