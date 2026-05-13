package org.edu.ntnu.idatt2003.group49.millions.helper;

import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.GameController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.GameSession;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.components.HeaderView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;
import org.edu.ntnu.idatt2003.group49.millions.view.landingpage.LandingPageView;

import java.math.BigDecimal;

public class ViewFactory {
  private final Navigator navigator;
  private final GameController gameController;

  public ViewFactory(Navigator navigator, GameController gameController) {
    this.navigator = navigator;
    this.gameController = gameController;
  }

  public HeaderView createHeaderView() {
    GameSession session = gameController.getActiveSession()
            .orElseThrow(() -> new IllegalStateException("No active game session"));
    return new HeaderView(navigator, session.getPlayer());
  }

  public DashboardView createDashboardView() {
    GameSession session = gameController.getActiveSession()
            .orElseThrow(() -> new IllegalStateException("No active game session"));
    Exchange exchange = session.getExchange();
    DashboardView dashboard = new DashboardView(navigator, new ExchangeController(exchange), createMillionsChart(exchange), createOwnedStocks());
    exchange.addObserver(dashboard);
    return dashboard;
  }

  public MillionsChart createMillionsChart(Exchange exchange) {
    Stock stock = exchange.getStock("NVDA");
    MillionsChart chart = new MillionsChart(stock.getCompany(), stock.getSalesPrice(), stock.getPriceChangeInPercent());
    return chart;
  }

  public OwnedStocks createOwnedStocks() {
    OwnedStocks ownedStocks = new OwnedStocks(navigator);
    return ownedStocks;
  }

  public LandingPageView createLandingPageView() {
    LandingPageView landingpage = new LandingPageView(navigator, gameController);

    return  landingpage;
  }
}
