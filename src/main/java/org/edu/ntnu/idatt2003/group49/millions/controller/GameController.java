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

/**
 * Handles setup and lifecycle state for the active game session.
 * Creates a new player, loads stock data, creates an exchange, and stores
 * the resulting {@link GameSession}.
 */
public class GameController {
  private GameSession activeSession;
  private final MillionsFileReader fileReader;

  /**
   * Creates a game controller with the file reader used to load stock data.
   *
   * @param fileReader reader used to load stocks from a selected file
   * @throws NullPointerException if fileReader is null
   */
  public GameController(MillionsFileReader fileReader) {
    this.fileReader = Objects.requireNonNull(fileReader, "fileReader requires non null");
  }

  /**
   * Returns the currently active game session, if one has been started.
   *
   * @return an Optional containing the active session, or empty if no game is active
   */
  public Optional<GameSession> getActiveSession() {
    return Optional.ofNullable(activeSession);
  }

  /**
   * Starts a new game using the provided player name, starting money, and stock data file.
   *
   * @param name the player's name
   * @param startingMoney the player's starting cash
   * @param csvPath path to the CSV file containing stock data
   * @return empty Optional if the game was started successfully, otherwise an error message
   */
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

  /**
   * Clears the active game session.
   */
  public void clearSession() {
    activeSession = null;
  }

}
