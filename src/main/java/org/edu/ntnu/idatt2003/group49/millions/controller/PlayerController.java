package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

  public BigDecimal getCurrentPortfolioValue() {
    return player.getPortfolio().getValue();
  }

  public Map<Integer, BigDecimal> getPortfolioValues() {
    return player.getPortfolio().getValueMap();
  }

  public BigDecimal getHighestPortfolioValue() {
    return player.getPortfolio().getHighestValue();
  }

  public BigDecimal getLowestPortfolioValue() {
    return player.getPortfolio().getLowestValue();
  }
}
