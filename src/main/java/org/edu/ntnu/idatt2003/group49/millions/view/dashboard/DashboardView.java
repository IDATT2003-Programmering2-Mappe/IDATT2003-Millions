package org.edu.ntnu.idatt2003.group49.millions.view.dashboard;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsGraph.MillionsGraph;
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
    HBox body = new HBox();
    VBox bodyLeft = new VBox();
    bodyLeft.getStyleClass().add("body-left");
    bodyLeft.setFillWidth(true);
    VBox bodyRight = new VBox();
    bodyRight.getStyleClass().add("body-right");
    bodyRight.setFillWidth(true);
    HBox.setHgrow(bodyLeft, Priority.ALWAYS);
    HBox.setHgrow(bodyRight, Priority.ALWAYS);

    Random rand = new Random();
    AtomicInteger week = new AtomicInteger(2);

    MillionsGraph chart = new MillionsGraph();
    chart.addData(new XYChart.Data<>(1, 50));

    Button advanceBtn = new Button("Advance");
    advanceBtn.setOnAction(e -> {
      XYChart.Data<Number, Number> data = new XYChart.Data<>(week.get(), rand.nextInt(0, 100));
      chart.addData(data);
      week.getAndIncrement();
    });

    bodyLeft.getChildren().addAll(
      chart,
      advanceBtn
    );

    bodyRight.getChildren().add(
      new OwnedStocks(this.navController)
    );

    body.getChildren().addAll(
      bodyLeft,
      bodyRight
    );

    return body;
  }
}
