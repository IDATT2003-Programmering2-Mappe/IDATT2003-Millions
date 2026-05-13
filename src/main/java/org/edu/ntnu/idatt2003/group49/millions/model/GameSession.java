package org.edu.ntnu.idatt2003.group49.millions.model;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;

import java.util.Objects;

public class GameSession {
  private final Player player;
  private final Exchange exchange;

  public GameSession(Player player, Exchange exchange) {
    this.player = Objects.requireNonNull(player, "player cannot be null");
    this.exchange = Objects.requireNonNull(exchange, "exchange cannot be null");
  }

  public Player getPlayer() {
    return player;
  }

  public Exchange getExchange() {
    return exchange;
  }
}
