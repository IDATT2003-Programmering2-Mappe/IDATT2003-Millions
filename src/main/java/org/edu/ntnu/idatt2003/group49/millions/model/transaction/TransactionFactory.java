package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Factory responsible for creating transaction objects.
 *
 * <p>The factory centralizes the creation of {@link Transaction} objects
 * and hides the details of how purchase and sale transactions are built.</p>
 */
public class TransactionFactory {

  /**
   * Creates a purchase transaction for a stock.
   *
   * @param stock the stock to buy
   * @param quantity the quantity to buy
   * @param week the current week
   * @return a purchase transaction as a Transaction
   * @throws NullPointerException if stock or quantity is null
   * @throws IllegalArgumentException if quantity is less than or equal to zero,
   *                                  or if week is negative
   */
  public Transaction createPurchase(Stock stock, BigDecimal quantity, int week) {
    Objects.requireNonNull(stock, "stock cannot be null");
    Objects.requireNonNull(quantity, "quantity cannot be null");

    if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("quantity must be greater than zero");
    }

    Share share = new Share(stock, quantity, stock.getSalesPrice());
    TransactionCalculator calculator = new PurchaseCalculator(share);

    return new Purchase(share, week, calculator);
  }

  /**
   * Creates a sale transaction for part of or all of an owned share.
   *
   * <p>The sale is represented by a new Share object containing only the
   * quantity that should be sold.</p>
   *
   * @param ownedShare the share currently owned by the player
   * @param quantityToSell the quantity to sell
   * @param week the current week
   * @return a sale transaction as a Transaction
   * @throws NullPointerException if ownedShare or quantityToSell is null
   * @throws IllegalArgumentException if quantityToSell is less than or equal to zero,
   *                                  greater than the owned quantity, or if week is negative
   */
  public Transaction createSale(Share ownedShare, BigDecimal quantityToSell, int week) {
    Objects.requireNonNull(ownedShare, "ownedShare cannot be null");
    Objects.requireNonNull(quantityToSell, "quantityToSell cannot be null");

    if (quantityToSell.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("quantity must be greater than zero");
    }
    if (quantityToSell.compareTo(ownedShare.getQuantity()) > 0) {
      throw new IllegalArgumentException("Cannot sell more shares than owned");
    }

    Share shareToSell = new Share(ownedShare.getStock(), quantityToSell, ownedShare.getPurchasePrice());
    TransactionCalculator calculator = new SaleCalculator(shareToSell);

    return new Sale(shareToSell, week, calculator);
  }
}
