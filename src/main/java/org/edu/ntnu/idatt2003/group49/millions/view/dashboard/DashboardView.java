package org.edu.ntnu.idatt2003.group49.millions.view.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.StockObserver;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsGraph.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class DashboardView extends MillionsView implements StockObserver {
  private final Navigator navigator;
  private final ExchangeController exchangeController;
  private final MillionsChart chart;

  private final ObservableList<BigDecimal> stockData = FXCollections.observableArrayList();

  public DashboardView(Navigator navigator, ExchangeController exchangeController) {
    this.navigator = navigator;
    this.exchangeController = exchangeController;
    this.chart = new MillionsChart();

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    Button advanceBtn = new Button("Advance");
    advanceBtn.setOnAction(e -> {
      exchangeController.advance();
    });

    VBox bodyLeft = new VBox();
    bodyLeft.getStyleClass().add("body-left");
    bodyLeft.setFillWidth(true);
    HBox.setHgrow(bodyLeft, Priority.ALWAYS);

    bodyLeft.getChildren().addAll(
      chart,
      advanceBtn
    );

    VBox bodyRight = new VBox();
    bodyRight.getStyleClass().add("body-right");
    bodyRight.setFillWidth(true);
    HBox.setHgrow(bodyRight, Priority.ALWAYS);

    bodyRight.getChildren().add(
      new OwnedStocks(this.navigator)
    );

    HBox body = new HBox();
    body.getStyleClass().add("dashboard");

    body.getChildren().addAll(
      bodyLeft,
      bodyRight
    );

    return body;
  }

  @Override
  public void update(Map<String, Stock> stockMap, int week) {
    System.out.println(stockMap.get("NVDA").getSalesPrice());
    XYChart.Data<Number, Number> data = new XYChart.Data<>(week, stockMap.get("NVDA").getSalesPrice());
    chart.addData(data);
  }
}
