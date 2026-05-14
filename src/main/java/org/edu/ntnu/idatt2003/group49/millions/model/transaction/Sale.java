package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Sale extends Transaction {

  private final List<SaleAllocation> allocations;

  public Sale(Share share, int week, TransactionCalculator calculator) {
    this(share, week, calculator, List.of(new SaleAllocation(share, share.getQuantity())));
  }

  public Sale(Share share, int week, TransactionCalculator calculator, List<SaleAllocation> allocations) {
    super(share, week, calculator);

    Objects.requireNonNull(allocations, "allocations cannot be null");
    if (allocations.isEmpty()) {
      throw new IllegalArgumentException("allocations cannot be empty");
    }

    this.allocations = List.copyOf(allocations);
  }
  @Override
  public void commit(Player player) {
    Objects.requireNonNull(player, "player cannot be null");

    if (commited) {
      throw new IllegalArgumentException("Transaction already commited");
    }

    BigDecimal totalCost = getCalculator().calculateTotal();

    player.getPortfolio().applySale(allocations);
    player.addMoney(totalCost);
    player.getTransactionArchive().add(this);
    commited = true;
  }
}
