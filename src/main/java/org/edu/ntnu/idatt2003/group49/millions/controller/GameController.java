package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.GameSession;

import java.io.FileReader;
import java.util.Objects;
import java.util.Optional;

public class GameController {
  private GameSession activeSession;
  private final FileReader fileReader;

  public GameController(FileReader fileReader) {
    this.fileReader = Objects.requireNonNull(fileReader, "fileReader requires non null");
  }
  public Optional<GameSession> getActiveSession() {
    return Optional.ofNullable(activeSession);
  }

  public void clearSession() {
    activeSession = null;
  }

}
