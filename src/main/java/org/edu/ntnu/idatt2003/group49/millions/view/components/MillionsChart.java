package org.edu.ntnu.idatt2003.group49.millions.view.components;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;

import java.util.Objects;

public class MillionsChart<X, Y> extends AreaChart<X, Y> {
  public MillionsChart(NavController navController) {
    super(navController);
    getChildren().add(build());
    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/chart.css")).toExternalForm());
  }

  @Override
  protected Pane build() {
    NumberAxis xAxis = new NumberAxis(1, 10, 1);
    NumberAxis yAxis = new NumberAxis(-1000, 1000, 100);

    LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.setName("Change");

    series.getData().add(new XYChart.Data<>(1, 15));
    series.getData().add(new XYChart.Data<>(2, 30));
    series.getData().add(new XYChart.Data<>(3, 60));
    series.getData().add(new XYChart.Data<>(4, 120));
    series.getData().add(new XYChart.Data<>(5, 240));
    series.getData().add(new XYChart.Data<>(6, 300));
    XYChart.Data<Number, Number> data = new XYChart.Data<>(7, -300);
    data.nodeProperty().addListener((obs, oldNode, newNode) -> {
      if (newNode != null) {
        newNode.setStyle("-fx-background-color: red");
        System.out.println("monkey");
      }
    });
    series.getData().add(data);
    series.getData().add(new XYChart.Data<>(8, 500));

    lineChart.getData().add(series);


    System.out.println(lineChart.getStyleClass());
    return new StackPane(lineChart);
  }
}
