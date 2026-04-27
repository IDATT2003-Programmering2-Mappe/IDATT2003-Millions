package org.edu.ntnu.idatt2003.group49.millions.model.calculator;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Class for calculations associated with purchases.
 */
public class PurchaseCalculator implements TransactionCalculator {
  private final BigDecimal purchasePrice;
  private final BigDecimal quantity;

  public PurchaseCalculator(Share share) {
    Objects.requireNonNull(share, "'share' cannot be null");
    this.purchasePrice = share.getPurchasePrice();
    this.quantity      = share.getQuantity();
  }

  /**
   * Calculates the gross value of the purchase as {@code purchasePrice * quantity}.
   *
   * @return the gross purchase value as a {@link BigDecimal}
   */
  @Override
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  /**
   * Calculates the commission of the purchase as {@code gross * 0.5%}
   *
   * @return the commission purchase value as a {@link BigDecimal}
   */
  @Override
  public BigDecimal calculateCommission() {
    BigDecimal commissionRate = new BigDecimal("0.005");

    return calculateGross().multiply(commissionRate)
            .setScale(2, RoundingMode.HALF_UP);
  }

  /**
   * Calculates the tax of the purchase. Purchases are not taxed.
   *
   * @return the tax amount as a {@link BigDecimal}
   */
  @Override
  public BigDecimal calculateTax() {
    return BigDecimal.ZERO;
  }

  /**
   * Calculates the purchase total as {@code gross + commission + tax}
   *
   * @return the purchase total as a {@link BigDecimal}
   */
  @Override
  public BigDecimal calculateTotal() {
    return calculateGross()
            .add(calculateCommission())
                    .add(calculateTax());
  }
}
