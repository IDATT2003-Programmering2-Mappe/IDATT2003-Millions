package org.edu.ntnu.idatt2003.group49.millions.view.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.StockObserver;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.PortfolioInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DashboardView extends MillionsView implements StockObserver {
  private final Navigator navigator;
  private final ExchangeController exchangeController;
  private final PortfolioInfo portfolioInfo;
  private final MillionsChart chart;
  private final OwnedStocks ownedStocks;

  private final ObservableList<BigDecimal> stockData = FXCollections.observableArrayList();

  public DashboardView(Navigator navigator, ExchangeController exchangeController, PortfolioInfo portfolioInfo, OwnedStocks ownedStocks) {
    this.navigator = navigator;
    this.exchangeController = exchangeController;
    this.portfolioInfo = portfolioInfo;
    this.chart = new MillionsChart();
    this.ownedStocks = ownedStocks;

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    HBox body = new HBox();
    body.getStyleClass().add("dashboard");

    body.getChildren().addAll(
      createLeftBody(),
      createRightBody()
    );

    Stock nvidiaStock = exchangeController.getStock("NVDA");
    List<BigDecimal> nvidiaPrices = exchangeController.getStockPrices(nvidiaStock.getSymbol());
    for (int i = 0; i < nvidiaPrices.size(); i++) {
      chart.addData(i, nvidiaPrices.get(i));
    }

    chart.setYBounds(nvidiaStock.getHighestPrice(), nvidiaStock.getLowestPrice());
    chart.updateYAxis();

    return body;
  }

  private VBox createLeftBody() {
    VBox leftBody = new VBox();
    leftBody.getStyleClass().add("body-left");
    leftBody.setFillWidth(true);
    HBox.setHgrow(leftBody, Priority.ALWAYS);

    HBox controls = new HBox();
    controls.getStyleClass().add("controls");
    Button advanceBtn = new Button("Advance");
    advanceBtn.getStyleClass().add("advance-btn");
    advanceBtn.setOnAction(e -> {
      exchangeController.advance();
    });
    controls.getChildren().addAll(advanceBtn);

    leftBody.getChildren().addAll(
      portfolioInfo,
      chart,
      controls
    );
    return leftBody;
  }

  private VBox createRightBody() {
    VBox rightBody = new VBox();
    rightBody.getStyleClass().add("body-right");
    rightBody.setFillWidth(true);
    HBox.setHgrow(rightBody, Priority.ALWAYS);

    rightBody.getChildren().add(
      ownedStocks
    );
    return rightBody;
  }

  @Override
  public void update(Map<String, Stock> stockMap, int week) {
    System.out.println(stockMap.get("NVDA").getSalesPrice());
    Stock stock = stockMap.get("NVDA");
    chart.addData(week, stock.getSalesPrice());
    chart.setYBounds(stock.getHighestPrice(), stock.getLowestPrice());
    chart.updateYAxis();
    portfolioInfo.updateInfoBar("Portfolio", stock.getSalesPrice(), stock.getCurrentChange());
  }
}
