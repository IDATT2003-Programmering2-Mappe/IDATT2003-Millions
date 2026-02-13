package org.edu.ntnu.idatt2003.group49.millions.transaction;

import org.edu.ntnu.idatt2003.group49.millions.Share;
import org.edu.ntnu.idatt2003.group49.millions.calculator.TransactionCalculator;

public abstract class Transaction {
  private Share share;
  private int week;
  private TransactionCalculator calculator;
  private boolean commited;

  protected Transaction(Share share, int week, TransactionCalculator calculator) {
    this.share      = share;
    this.week       = week;
    this.calculator = calculator;
  }

  public Share getShare() {
    return share;
  }

  public int getWeek() {
    return week;
  }

  public TransactionCalculator getCalculator() {
    return calculator;
  }

  public boolean isCommited() {
    return commited;
  }

  public void commit(Player player) {

  }
}
