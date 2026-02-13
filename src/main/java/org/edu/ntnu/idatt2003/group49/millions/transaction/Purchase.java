package org.edu.ntnu.idatt2003.group49.millions.transaction;

import org.edu.ntnu.idatt2003.group49.millions.Share;
import org.edu.ntnu.idatt2003.group49.millions.calculator.TransactionCalculator;

public class Purchase extends Transaction {
  protected Purchase(Share share, int week, TransactionCalculator calculator) {
    super(share, week, calculator);
  }

  // TODO:
  @Override
  public void commit(Player player) {
    super.commit(player);
  }
}
