package org.edu.ntnu.idatt2003.group49.millions.view.dashboard;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.Observer;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsGraph.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class DashboardView extends MillionsView implements Observer {
  private final Navigator navigator;
  private final ExchangeController exchangeController;
  private ObservableList<BigDecimal> stockData;

  public DashboardView(Navigator navigator, ExchangeController exchangeController) {
    this.navigator = navigator;
    this.exchangeController = exchangeController;

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
//    Random rand = new Random();
//    AtomicInteger week = new AtomicInteger(2);

    MillionsChart chart = new MillionsChart();
//    chart.addData(new XYChart.Data<>(1, 50));

    Button advanceBtn = new Button("Advance");
    advanceBtn.setOnAction(e -> {
//      XYChart.Data<Number, Number> data = new XYChart.Data<>(week.get(), rand.nextInt(0, 100));
//      chart.addData(data);
//      week.getAndIncrement();
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
  public void update(List<BigDecimal> stockPrices) {

  }
}
