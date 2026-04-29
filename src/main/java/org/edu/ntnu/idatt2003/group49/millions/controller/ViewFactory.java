package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.view.components.HeaderView;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.DashboardView;

public class ViewFactory {
  private final Navigator navigator;
  private final ExchangeController exchangeController;

  public ViewFactory(Navigator navigator, ExchangeController exchangeController) {
    this.navigator = navigator;
    this.exchangeController = exchangeController;
  }

  public HeaderView createHeaderView() {
    return new HeaderView(navigator);
  }

  public DashboardView createDashboardView() {
    return new DashboardView(navigator, exchangeController);
  }
}
