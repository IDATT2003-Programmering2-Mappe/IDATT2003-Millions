package org.edu.ntnu.idatt2003.group49.millions.controller;

import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.helper.ViewFactory;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class NavController implements Navigator {
  private final Logger logger = Logger.getLogger(getClass().getName());
  private ViewFactory viewFactory;
  private final BorderPane root;
  private final GameController gameController;

  public NavController(BorderPane root, GameController gameController) {
    this.root = root;
    this.gameController = gameController;
  }

  public void setViewFactory(ViewFactory viewFactory) {
    this.viewFactory = viewFactory;
  }

  @Override
  public void goToDashboard() {
    root.setTop(viewFactory.createHeaderView());
    root.setCenter(viewFactory.createDashboardView());
    logger.info("Switched to Dashboard");
  }

  @Override
  public void goToLandingPage() {
    root.setTop(null);
    root.setCenter(viewFactory.createLandingPageView());
    logger.info("Switched to Landing page");
  }

  @Override
  public Optional<String> startNewGame(String name, BigDecimal startingMoney, Path csvPath) {
    Optional<String> error = gameController.startNewGame(name, startingMoney, csvPath);

    if (error.isPresent()) {
      return error;
    }
    goToDashboard();
    return Optional.empty();
  }
}
