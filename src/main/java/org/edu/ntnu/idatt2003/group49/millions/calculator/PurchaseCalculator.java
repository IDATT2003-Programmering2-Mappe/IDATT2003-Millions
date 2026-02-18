package org.edu.ntnu.idatt2003.group49.millions.calculator;

import org.edu.ntnu.idatt2003.group49.millions.Share;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class PurchaseCalculator implements TransactionCalculator {
  private final BigDecimal purchasePrice;
  private final BigDecimal quantity;

  public PurchaseCalculator(Share share) {
    Objects.requireNonNull(share, "'share' cannot be null");
    this.purchasePrice = share.getPurchasePrice();
    this.quantity      = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    BigDecimal commissionRate = new BigDecimal("0.005");

    return calculateGross().multiply(commissionRate)
            .setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public BigDecimal calculateTax() {
    return BigDecimal.ZERO;
  }

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross()
            .add(calculateCommission())
                    .add(calculateTax());
  }
}
