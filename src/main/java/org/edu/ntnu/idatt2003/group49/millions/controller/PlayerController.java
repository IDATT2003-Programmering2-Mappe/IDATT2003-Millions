package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class PlayerController {
  private final Player player;

  public PlayerController(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public String getName() {
    return player.getName();
  }

  public BigDecimal getMoney() {
    return player.getMoney();
  }

  public BigDecimal getPortfolioChange() {
    return player.getPortfolio().getCurrentChange();
  }

  public BigDecimal getPortfolioValue() {
    return player.getPortfolio().getValue();
  }

  public List<Transaction> getTransactions() {
    return player.getTransactionArchive().getAllTransactions();
  }
}
