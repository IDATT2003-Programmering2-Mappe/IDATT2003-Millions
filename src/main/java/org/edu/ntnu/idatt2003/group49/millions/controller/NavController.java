package org.edu.ntnu.idatt2003.group49.millions.controller;

import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.view.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.Header;

import java.util.logging.Logger;

public class NavController {
  private final Logger logger = Logger.getLogger(getClass().getName());
  private final BorderPane root;

  public NavController(BorderPane root) {
    this.root = root;
  }

  public void showDashboardView() {
    root.setTop(new Header(this));
    root.setCenter(new DashboardView(this));
    logger.info("Switched to DashboardView");
  }
}
