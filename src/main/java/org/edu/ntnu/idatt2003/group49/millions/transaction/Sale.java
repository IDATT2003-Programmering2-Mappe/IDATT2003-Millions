package org.edu.ntnu.idatt2003.group49.millions.transaction;

import org.edu.ntnu.idatt2003.group49.millions.Share;
import org.edu.ntnu.idatt2003.group49.millions.calculator.TransactionCalculator;
import org.edu.ntnu.idatt2003.group49.millions.Player;

import java.math.BigDecimal;
import java.util.Objects;

public class Sale extends Transaction {
  public Sale(Share share, int week, TransactionCalculator calculator) {
    super(share, week, calculator);
  }

  @Override
  public void commit(Player player) {
    Objects.requireNonNull(player, "player cannot be null");

    if (isCommited()) {
      throw new IllegalArgumentException("Transaction already commited");
    }

    BigDecimal totalCost = getCalculator().calculateTotal();

    player.addMoney(totalCost);
    player.getPortfolio().removeShare(getShare());
    player.getTransactionArchive().add(this);
    markCommited();
  }
}
