package org.edu.ntnu.idatt2003.group49.millions.view.dashboard;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart;
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
    VBox leftSection = new VBox();
    VBox rightSection = new VBox();

    Random rand = new Random();
    AtomicInteger week = new AtomicInteger(2);

    MillionsChart chart = new MillionsChart();

    Button advanceBtn = new Button("Advance");
    advanceBtn.setOnAction(e -> {
      chart.addData(new XYChart.Data<>(week, rand.nextInt(0, 100)));
      week.getAndIncrement();
    });

    leftSection.getChildren().addAll(
      chart,
      advanceBtn
    );

    rightSection.getChildren().add(
      new OwnedStocks(this.navController)
    );

    body.getChildren().addAll(
      leftSection,
      rightSection
    );

    return body;
  }
}
