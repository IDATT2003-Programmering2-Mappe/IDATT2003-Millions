package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.Player;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase extends Transaction {
  public Purchase(Share share, int week, TransactionCalculator calculator) {
    super(share, week, calculator);
  }

  @Override
  public void commit(Player player) {
    Objects.requireNonNull(player, "player cannot be null");

    if (isCommited()) {
      throw new IllegalStateException("Transaction already commited");
    }
    if (player.getMoney().compareTo(getCalculator().calculateTotal()) < 0) {
      throw new IllegalStateException("player '" + player.getName() + "' has insufficient funds to make this purchase");
    }

    BigDecimal totalCost = getCalculator().calculateTotal();

    player.withdrawMoney(totalCost);
    player.getPortfolio().addShare(getShare());
    player.getTransactionArchive().add(this);
    markCommited();
    }
}
