package org.edu.ntnu.idatt2003.group49.millions.helper;

import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.components.HeaderView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TradingTable;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.PortfolioInfo;
import org.edu.ntnu.idatt2003.group49.millions.view.landingpage.LandingPageView;
import org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.TradingPageView;

import java.math.BigDecimal;

public class ViewFactory {
  private final Navigator navigator;
  private final Exchange exchange;

  public ViewFactory(Navigator navigator, Exchange exchange) {
    this.navigator = navigator;
    this.exchange = exchange;
  }

  public HeaderView createHeaderView() {
    HeaderView header = new HeaderView(navigator);
    return new HeaderView(navigator);
  }

  public DashboardView createDashboardView() {
    DashboardView dashboard = new DashboardView(navigator, new ExchangeController(exchange), createPortfolioInfo(), createMillionsChart(), createOwnedStocks());
    exchange.addObserver(dashboard);
    return dashboard;
  }

  public PortfolioInfo createPortfolioInfo() {
    Stock stock = this.exchange.getStock("NVDA");
    return new PortfolioInfo("Portfolio", stock.getSalesPrice(), stock.getPriceChangeInPercent());
  }

  public MillionsChart createMillionsChart() {
    MillionsChart chart = new MillionsChart();
    return chart;
  }

  public OwnedStocks createOwnedStocks() {
    OwnedStocks ownedStocks = new OwnedStocks(navigator);
    return ownedStocks;
  }

  public LandingPageView createLandingPageView() {
    LandingPageView landingpage = new LandingPageView(navigator);

    return  landingpage;
  }

  public TradingPageView createTradingPageView() {
    return new TradingPageView(createTradingTable(), createMillionsChart());
  }

  public TradingTable createTradingTable() {
    return new TradingTable(navigator);
  }
}
