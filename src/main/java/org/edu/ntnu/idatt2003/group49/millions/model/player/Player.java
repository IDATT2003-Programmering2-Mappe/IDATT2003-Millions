package org.edu.ntnu.idatt2003.group49.millions.model.player;

import java.math.BigDecimal;
import java.util.Objects;

public class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;

  public Player(String name, BigDecimal startingMoney) {
    this.name = Objects.requireNonNull(name, "name cannot be null");
    if (this.name.isBlank()) {
      throw new IllegalArgumentException("name must have at least one character");
    }
    this.startingMoney = Objects.requireNonNull(startingMoney, "startingMoney cannot be null");
    if (this.startingMoney.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("startingMoney must be greater than zero");
    }
    this.money = startingMoney;
    this.portfolio = new Portfolio();
    this.transactionArchive = new TransactionArchive();
  }

  public String getName() {
    return name;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public Status getStatus() {
    BigDecimal netWorth = getNetWorth();
    int weeksTraded = transactionArchive.countDistinctWeeks();

    BigDecimal investorThreshold = startingMoney.multiply(new BigDecimal("1.20"));
    BigDecimal speculatorThreshold = startingMoney.multiply(new BigDecimal("2.00"));

    if (weeksTraded >= 20 &&
        netWorth.compareTo(speculatorThreshold) >= 0) {
      return Status.SPECULATOR;
    }

    if (weeksTraded >= 10 &&
        netWorth.compareTo(investorThreshold) >= 0) {
      return Status.INVESTOR;
    }

    return Status.NOVICE;
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

