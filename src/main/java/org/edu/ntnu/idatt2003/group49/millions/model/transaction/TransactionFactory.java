package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionFactory {

  public Purchase createPurchase(Stock stock, BigDecimal quantity, int week) {
    Objects.requireNonNull(stock, "stock cannot be null");
    Objects.requireNonNull(quantity, "quantity cannot be null");

    if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("quantity must be greater than zero");
    }

    Share share = new Share(stock, quantity, stock.getSalesPrice());
    TransactionCalculator calculator = new PurchaseCalculator(share);

    return new Purchase(share, week, calculator);
  }

  public Sale createSale(Share ownedShare, BigDecimal quantityToSell, int week) {
    Objects.requireNonNull(ownedShare, "stock cannot be null");
    Objects.requireNonNull(quantityToSell, "quantity cannot be null");

    if (quantityToSell.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("quantity must be greater than zero");
    }
    if (quantityToSell.compareTo(ownedShare.getQuantity()) > 0) {
      throw new IllegalArgumentException("Cannot selle more shares than owned");
    }

    Share shareToSell = new Share(ownedShare.getStock(), quantityToSell, ownedShare.getPurchasePrice());
    TransactionCalculator calculator = new SaleCalculator(shareToSell);

    return new Sale(shareToSell, week, calculator);
  }
}
