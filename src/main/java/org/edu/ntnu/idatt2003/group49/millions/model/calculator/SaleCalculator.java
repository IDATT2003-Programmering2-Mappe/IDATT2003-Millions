package org.edu.ntnu.idatt2003.group49.millions.model.calculator;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class SaleCalculator implements TransactionCalculator {
  private final BigDecimal totalPurchaseCost;
  private final BigDecimal salesPrice;
  private final BigDecimal quantity;

  public SaleCalculator(Share share) {
    this(Objects.requireNonNull(share, "'share' cannot be null"),
            share.getPurchasePrice().multiply(share.getQuantity())
    );
  }

  public SaleCalculator(Share share, BigDecimal totalPurchaseCost) {
    Objects.requireNonNull(share, "'share' cannot be null");
    Objects.requireNonNull(totalPurchaseCost, "'totalPurchaseCost' cannot be null");

    if (totalPurchaseCost.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("totalPurchasePrice cannot be negative");
    }

    this.totalPurchaseCost = totalPurchaseCost;
    this.salesPrice = share.getStock().getSalesPrice();
    this.quantity = share.getQuantity();

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
    BigDecimal gain = calculateGross().subtract(calculateCommission()).subtract(totalPurchaseCost);

    return taxRate.multiply(gain);
  }

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross()
            .subtract(calculateCommission())
            .subtract(calculateTax());
  }
}
