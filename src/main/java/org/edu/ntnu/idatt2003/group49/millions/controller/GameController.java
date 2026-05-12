package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.GameSession;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.MillionsFileReader;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GameController {
  private GameSession activeSession;
  private final MillionsFileReader fileReader;

  public GameController(MillionsFileReader fileReader) {
    this.fileReader = Objects.requireNonNull(fileReader, "fileReader requires non null");
  }

  public Optional<GameSession> getActiveSession() {
    return Optional.ofNullable(activeSession);
  }

  public Optional<String> startNewGame(String name, BigDecimal startingMoney, Path csvPath) {
    try{
      Player player = new Player(name, startingMoney);
      List<Stock> stocks = fileReader.readStocks(csvPath);
      Exchange exchange = new Exchange("S&P 500", stocks);

      activeSession = new GameSession(player, exchange);

      return Optional.empty();

    } catch (Exception e) {
      return Optional.of("Could not start game");
    }

  }

  public void clearSession() {
    activeSession = null;
  }

}
