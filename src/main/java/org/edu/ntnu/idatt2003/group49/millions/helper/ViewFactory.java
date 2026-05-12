package org.edu.ntnu.idatt2003.group49.millions.helper;

import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.components.HeaderView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;

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
    DashboardView dashboard = new DashboardView(navigator, new ExchangeController(exchange), createMillionsChart(), createOwnedStocks());
    exchange.addObserver(dashboard);
    return dashboard;
  }

  public MillionsChart createMillionsChart() {
    Stock stock = exchange.getStock("NVDA");
    MillionsChart chart = new MillionsChart(stock.getCompany(), stock.getSalesPrice(), stock.getPriceChangeInPercent());
    return chart;
  }

  public OwnedStocks createOwnedStocks() {
    OwnedStocks ownedStocks = new OwnedStocks(navigator);
    return ownedStocks;
  }
}
