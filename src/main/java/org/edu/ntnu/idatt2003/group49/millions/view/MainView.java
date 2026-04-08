package org.edu.ntnu.idatt2003.group49.millions.view;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.MainController;
import org.edu.ntnu.idatt2003.group49.millions.view.components.Header;

public class MainView extends BorderPane {
  private final MainController mainController;

  private final Header header;
  private final HomeView homeView;
  private final StocksView stocksView;

  public MainView(MainController controller) {
    this.mainController = controller;

    this.header = new Header();
    this.homeView = new HomeView();
    this.stocksView = new StocksView();

    setTop(header);

    setCenter(createPage(homeView));
  }

  private ScrollPane createPage(Node content) {
    ScrollPane page = new ScrollPane();
    page.setFitToWidth(true);
    page.setFitToHeight(true);
    page.setContent(content);
    return page;
  }

  public void showHomeView() {
    setCenter(createPage(homeView));
  }

  public void showStocksView() {
    setCenter(createPage(stocksView));
  }
}
