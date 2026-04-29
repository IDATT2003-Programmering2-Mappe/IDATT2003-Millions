package org.edu.ntnu.idatt2003.group49.millions.controller;

import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.HeaderView;

import java.util.logging.Logger;

public class NavController implements Navigator {
  private final Logger logger = Logger.getLogger(getClass().getName());
  private ViewFactory viewFactory;
  private final BorderPane root;

  public NavController(BorderPane root) {
    this.root = root;
  }

  public void setViewFactory(ViewFactory viewFactory) {
    this.viewFactory = viewFactory;
  }

  @Override
  public void goToDashboard() {
    root.setTop(viewFactory.createHeaderView());
    root.setCenter(viewFactory.createDashboardView());
    logger.info("Switched to DashboardView");
  }
}
