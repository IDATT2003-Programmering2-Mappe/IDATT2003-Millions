package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;

import java.util.Objects;

public abstract class Transaction {
  private final Share share;
  private final int week;
  private final TransactionCalculator calculator;
  protected boolean commited;

  protected Transaction(Share share, int week, TransactionCalculator calculator) {
    if (week < 0) {
      throw new IllegalArgumentException("Week cannot be negative");
    }
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

  public abstract void commit(Player player);
}
