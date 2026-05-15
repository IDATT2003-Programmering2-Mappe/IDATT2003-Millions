package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;

import java.math.BigDecimal;

public class PlayerController {
  private final Player player;

  public PlayerController(Player player) {
    this.player = player;
  }

  public String getName() {
    return player.getName();
  }

  public BigDecimal getPortfolioChange() {
    return player.getPortfolio().getCurrentChange();
  }

  public BigDecimal getPortfolioValue() {
    return player.getPortfolio().getValue();
  }
}
