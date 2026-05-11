package org.edu.ntnu.idatt2003.group49.millions.helper;

import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.view.components.HeaderView;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.landingpage.LandingPageView;

public class ViewFactory {
  private final Navigator navigator;
  private final Exchange exchange;

  public ViewFactory(Navigator navigator, Exchange exchange) {
    this.navigator = navigator;
    this.exchange = exchange;
  }

  public HeaderView createHeaderView() {
    return new HeaderView(navigator);
  }

  public DashboardView createDashboardView() {
    DashboardView dashboard = new DashboardView(navigator, new ExchangeController(exchange));
    exchange.addObserver(dashboard);
    return dashboard;
  }

  public LandingPageView createLandingPageView() {
    LandingPageView landingpage = new LandingPageView(navigator);

    return  landingpage;
  }
}
