package org.edu.ntnu.idatt2003.group49.millions.calculator;

import org.edu.ntnu.idatt2003.group49.millions.Share;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class SaleCalculator implements TransactionCalculator {
  private final BigDecimal purchasePrice;
  private final BigDecimal salesPrice;
  private final BigDecimal quantity;

  public SaleCalculator(Share share) {
    Objects.requireNonNull(share, "'share' cannot be null");
    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice    = share.getStock().getSalesPrice();
    this.quantity      = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return salesPrice.multiply(quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    BigDecimal commissionRate = new BigDecimal("0.01");

    return calculateGross().multiply(commissionRate)
            .setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public BigDecimal calculateTax() {
    BigDecimal taxRate = new BigDecimal("0.30");

    return taxRate.multiply(calculateGross()
                    .subtract(calculateCommission())
                    .subtract(purchasePrice.multiply(quantity)));
  }

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross()
            .subtract(calculateCommission())
            .subtract(calculateTax());
  }
}
