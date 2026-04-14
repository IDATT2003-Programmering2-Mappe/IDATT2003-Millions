package org.edu.ntnu.idatt2003.group49.millions.model.exchange;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Represents a share of a particular stock.
 * A share contains information about an owned stock and the quantity of shares owned,
 * and the purchase price per share.
 * The class is immutable.
 */
public class Share {
  private final Stock stock;
  private final BigDecimal quantity;
  private final BigDecimal purchasePrice;

  /**
   * Constructs a Share object representing the owned quantity of a specific stock
   * and the purchase price per share.
   *
   * @param stock          the stock associated with the share (cannot be null)
   * @param quantity       the quantity of shares owned (must be non-negative and cannot be null)
   * @param purchasePrice  the purchase price per share (must be non-negative and cannot be null)
   * @throws NullPointerException     if any of the parameters are null
   * @throws IllegalArgumentException if quantity or purchasePrice is negative
   */
  public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
    this.stock = Objects.requireNonNull(stock, "Stock cannot be null");
    this.quantity = Objects.requireNonNull(quantity, "Quantity cannot be null");
    this.purchasePrice = Objects.requireNonNull(purchasePrice, "Purchase price cannot be null");
    if (quantity.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    if (purchasePrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Purchase price cannot be negative");
    }
  }

  /**
   * Returns the stock associated with this share.
   *
   * @return the stock associated with this share
   */
  public Stock getStock() {
    return stock;
  }

  /**
   * Return the quantity of shares owned.
   *
   * @return the quantity of shares
   */
  public BigDecimal getQuantity() {
    return quantity;
  }

  /**
   * returns the price per share
   *
   * @return the purchase price per share
   */
  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }
}
