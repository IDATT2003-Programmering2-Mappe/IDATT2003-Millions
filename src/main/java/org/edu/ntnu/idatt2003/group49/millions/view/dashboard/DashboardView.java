package org.edu.ntnu.idatt2003.group49.millions.view.dashboard;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsGraph.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components.OwnedStocks;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class DashboardView extends MillionsView {
  private final NavController navController;

  public DashboardView(NavController navController) {
    this.navController = navController;
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    Random rand = new Random();
    AtomicInteger week = new AtomicInteger(2);

    MillionsChart chart = new MillionsChart();
    chart.addData(new XYChart.Data<>(1, 50));

    Button advanceBtn = new Button("Advance");
    advanceBtn.setOnAction(e -> {
      XYChart.Data<Number, Number> data = new XYChart.Data<>(week.get(), rand.nextInt(0, 100));
      chart.addData(data);
      week.getAndIncrement();
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
      new OwnedStocks(this.navController)
    );

    HBox body = new HBox();
    body.getStyleClass().add("dashboard");

    body.getChildren().addAll(
      bodyLeft,
      bodyRight
    );

    return body;
  }
}
