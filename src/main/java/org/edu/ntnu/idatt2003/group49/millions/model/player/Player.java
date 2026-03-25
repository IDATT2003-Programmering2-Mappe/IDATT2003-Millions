package org.edu.ntnu.idatt2003.group49.millions.model.player;

import java.math.BigDecimal;
import java.util.Objects;

public class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;
  private Status status;

  public Player(String name, BigDecimal startingMoney) {
    this.name = Objects.requireNonNull(name, "name cannot be null");
    if (this.name.isBlank()) {
      throw new IllegalArgumentException("name must have at least one character");
    }
    this.startingMoney = Objects.requireNonNull(startingMoney, "startingMoney cannot be null");
    if (this.startingMoney.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("startingMoney must be greater than zero");
    }
    this.money              = startingMoney;
    this.portfolio          = new Portfolio();
    this.transactionArchive = new TransactionArchive();
    this.status             = Status.NOVICE;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void addMoney(BigDecimal amount) {
    Objects.requireNonNull(amount, "amount cannot be null");
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("amount cannot be negative");
    }
    money = money.add(amount);
  }

  public void withdrawMoney(BigDecimal amount) {
    Objects.requireNonNull(amount, "amount cannot be null");
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("amount cannot be negative");
    }

    if (money.compareTo(amount) < 0) {
      throw new IllegalStateException("Not enough money");
    }
    money = money.subtract(amount);
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }

  public TransactionArchive getTransactionArchive() {
    return transactionArchive;
  }

  public BigDecimal getNetWorth() {
    return money.add(portfolio.getNetWorth());
  }
}
