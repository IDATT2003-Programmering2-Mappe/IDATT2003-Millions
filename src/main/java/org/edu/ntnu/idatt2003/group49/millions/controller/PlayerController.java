package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

  public void addValue(int week) {
    player.getPortfolio().addValue(week, player.getPortfolio().getValue());
  }

  public List<Share> getOwnedShares() {
    return player.getPortfolio().getShares();
  }

  public BigDecimal getPortfolioChange() {
    return player.getPortfolio().getChangeSinceFirstPurchase();
  }

  public BigDecimal getCurrentPortfolioValue() {
    return player.getPortfolio().getValue();
  }

  public Map<Integer, BigDecimal> getPortfolioValueMap() {
    return player.getPortfolio().getValueMap();
  }

  public BigDecimal getHighestPortfolioValue() {
    return player.getPortfolio().getHighestValue();
  }

  public BigDecimal getLowestPortfolioValue() {
    return player.getPortfolio().getLowestValue();
  }
}
