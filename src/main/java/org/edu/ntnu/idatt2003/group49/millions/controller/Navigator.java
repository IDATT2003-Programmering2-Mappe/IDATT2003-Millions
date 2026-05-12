package org.edu.ntnu.idatt2003.group49.millions.controller;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Optional;

public interface Navigator {
  void goToDashboard();
  void goToLandingPage();
  Optional<String> startNewGame(String name, BigDecimal startingMoney, Path csvPath);
}
