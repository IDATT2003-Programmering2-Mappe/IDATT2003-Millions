package org.edu.ntnu.idatt2003.group49.millions.transaction;

import org.edu.ntnu.idatt2003.group49.millions.Share;
import org.edu.ntnu.idatt2003.group49.millions.calculator.TransactionCalculator;
import org.edu.ntnu.idatt2003.group49.millions.Player;

import java.util.Objects;

public abstract class Transaction {
  private final Share share;
  private final int week;
  private final TransactionCalculator calculator;
  private boolean commited;

  protected Transaction(Share share, int week, TransactionCalculator calculator) {
    this.share      = Objects.requireNonNull(share, "share cannot be null");
    this.week       = week;
    this.calculator = Objects.requireNonNull(calculator,"calculator cannot be null");
    this.commited   = false;
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

  public void markCommited() {
    this.commited = true;
  }

  public void commit(Player player) {
  }
}
