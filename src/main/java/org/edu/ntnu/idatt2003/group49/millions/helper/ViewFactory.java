package org.edu.ntnu.idatt2003.group49.millions.helper;

import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.components.HeaderView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.OwnedSharesTable;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TradingTable;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory.OwnedSharesColumnFactory;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory.StocksColumnFactory;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.PortfolioInfo;
import org.edu.ntnu.idatt2003.group49.millions.view.landingpage.LandingPageView;
import org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.TradingPageView;
import org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components.StockInfo;

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
    DashboardView dashboard = new DashboardView(navigator, new ExchangeController(exchange), createPortfolioInfo(), createOwnedStocks());
    exchange.addObserver(dashboard);
    return dashboard;
  }

  public PortfolioInfo createPortfolioInfo() {
    Stock stock = this.exchange.getStock("NVDA");
    return new PortfolioInfo("Portfolio", stock.getSalesPrice(), stock.getCurrentChange());
  }

  public OwnedStocks createOwnedStocks() {
    OwnedStocks ownedStocks = new OwnedStocks(navigator, createOwnedSharesTable());
    return ownedStocks;
  }

  public LandingPageView createLandingPageView() {
    LandingPageView landingpage = new LandingPageView(navigator);

    return  landingpage;
  }

  public TradingPageView createTradingPageView() {
    return new TradingPageView(new ExchangeController(exchange), createTradingTable(), createStockInfo());
  }

  public OwnedSharesTable createOwnedSharesTable() {
    return new OwnedSharesTable(navigator, new OwnedSharesColumnFactory());
  }

  public TradingTable createTradingTable() {
    return new TradingTable(navigator, new StocksColumnFactory());
  }

  public StockInfo createStockInfo() {
    return new StockInfo();
  }
}
