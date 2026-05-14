package org.edu.ntnu.idatt2003.group49.millions.helper;

import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.GameController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.GameSession;
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
    LandingPageView landingpage = new LandingPageView(navigator, gameController);

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
