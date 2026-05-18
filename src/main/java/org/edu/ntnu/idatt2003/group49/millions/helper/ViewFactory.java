package org.edu.ntnu.idatt2003.group49.millions.helper;

import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.GameController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.model.GameSession;
import org.edu.ntnu.idatt2003.group49.millions.view.pages.HeaderView;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.TableSelectionModel;
import org.edu.ntnu.idatt2003.group49.millions.view.pages.dashboard.DashboardView;
import org.edu.ntnu.idatt2003.group49.millions.view.pages.landingpage.LandingPageView;
import org.edu.ntnu.idatt2003.group49.millions.view.pages.tradingpage.TradingPageView;

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
    DashboardView dashboard = new DashboardView(navigator, new ExchangeController(session.getExchange()), new PlayerController(session.getPlayer()), new TableSelectionModel<>());
    session.getExchange().addObserver(dashboard);
    return dashboard;
  }

  public LandingPageView createLandingPageView() {
    LandingPageView landingpage = new LandingPageView(navigator, gameController);

    return  landingpage;
  }

  public TradingPageView createTradingPageView() {
    GameSession session = gameController.getActiveSession()
      .orElseThrow(() -> new IllegalStateException("No active game session"));
    return new TradingPageView(new ExchangeController(session.getExchange()), new PlayerController(session.getPlayer()), new TableSelectionModel<>());
  }
}
