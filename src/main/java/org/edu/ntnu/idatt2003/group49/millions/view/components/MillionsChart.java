package org.edu.ntnu.idatt2003.group49.millions.view.components;

import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class MillionsChart extends MillionsView {
  private final AreaChart<Number, Number> chart;
  private final XYChart.Series<Number, Number> mainSeries;
  private NumberAxis xAxis;

  public MillionsChart() {
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/chart.css")
    ).toExternalForm());

    this.chart = create();
    this.mainSeries = new XYChart.Series<>();
    this.chart.getData().add(mainSeries);
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    this.chart.setCreateSymbols(false);
    testData();
    return new StackPane(this.chart);
  }

  private AreaChart<Number, Number> create() {
    this.xAxis = new NumberAxis(1, 1, 1);
    xAxis.setLabel("Weeks");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Value");

    return new AreaChart<>(xAxis, yAxis);
  }

  public void addData(XYChart.Data<Number, Number> data) {
    this.xAxis.setUpperBound(data.getXValue().doubleValue());
    this.mainSeries.getData().add(data);
  }

  private void testData() {
    addData(new XYChart.Data<>(1, 0));
    addData(new XYChart.Data<>(2, 3));
    addData(new XYChart.Data<>(3, 10));
    addData(new XYChart.Data<>(4, 50));
    addData(new XYChart.Data<>(5, 30));
  }
}
