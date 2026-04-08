package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.MillionsService;
import org.edu.ntnu.idatt2003.group49.millions.view.MainView;

public class MainController {
  private final MainView view;
  private final HeaderController headerController;
  private final MillionsService millions;

  public MainController(MillionsService millions) {
    this.view = new MainView(this);
    this.headerController = new HeaderController(this);
    this.millions = millions;
  }

  public HeaderController getHeaderController() {
    return this.headerController;
  }

  public MainView getMainView() {
    return view;
  }

  public void showHomeView() {
    view.showHomeView();
  }

  public void showStocksView() {
    view.showStocksView();
  }
}
