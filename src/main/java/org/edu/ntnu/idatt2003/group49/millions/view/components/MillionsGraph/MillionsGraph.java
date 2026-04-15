package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsGraph;

import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class MillionsGraph extends MillionsView {
  private final AreaChart<Number, Number> graph;
  private final XYChart.Series<Number, Number> series;
  private NumberAxis xAxis;
  private GraphMode mode = GraphMode.MAX;

  public MillionsGraph() {
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/graph.css")
    ).toExternalForm());

    this.graph = create();
    this.series = new XYChart.Series<>();
    this.graph.getData().add(series);
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    this.graph.setAnimated(false);
    this.graph.setCreateSymbols(false);
    this.graph.setLegendVisible(false);

    VBox component = new VBox();
    component.getChildren().addAll(
      filters(),
      this.graph
    );
    return component;
  }

  private AreaChart<Number, Number> create() {
    this.xAxis = new NumberAxis(1, 2, 1);
    NumberAxis yAxis = new NumberAxis();

    yAxis.setTickLabelFormatter(new StringConverter<Number>() {
      @Override
      public String toString(Number value) {
        return String.format("%.0f", value.doubleValue()) + "%";
      }

      @Override
      public Number fromString(String string) {
        return Double.parseDouble(string.replace("%", ""));
      }
    });

    return new AreaChart<>(xAxis, yAxis);
  }

  public void addData(XYChart.Data<Number, Number> data) {
    xAxis.setUpperBound(data.getXValue().doubleValue());
    changeLowerBoundBasedOnMode();
    this.series.getData().add(data);
  }

  private void changeLowerBoundBasedOnMode() {
    switch (mode) {
      case ONE_MONTH -> xAxis.setLowerBound(xAxis.getUpperBound() - 4);
      case THREE_MONTHS -> xAxis.setLowerBound(xAxis.getUpperBound() - 12);
      case SIX_MONTHS -> xAxis.setLowerBound(xAxis.getUpperBound() - 24);
      case ONE_YEAR -> xAxis.setLowerBound(xAxis.getUpperBound() - 48);
      case MAX -> xAxis.setLowerBound(1);
    }
  }

  private void testData() {
    addData(new XYChart.Data<>(1, 0));
    addData(new XYChart.Data<>(2, 3));
    addData(new XYChart.Data<>(3, 10));
    addData(new XYChart.Data<>(4, 50));
    addData(new XYChart.Data<>(5, 30));
  }

  private HBox filters() {
    Button oneMonthBtn = new Button("1M");
    oneMonthBtn.setOnAction(e -> {
      if (this.xAxis.getUpperBound() > 4) {
        this.mode = GraphMode.ONE_MONTH;
        changeLowerBoundBasedOnMode();
      }
    });

    Button threeMonthsBtn = new Button("3M");
    threeMonthsBtn.setOnAction(e -> {
      if (this.xAxis.getUpperBound() > 13) {
        this.mode = GraphMode.THREE_MONTHS;
        changeLowerBoundBasedOnMode();
      }
    });

    Button sixMonthsBtn = new Button("6M");
    sixMonthsBtn.setOnAction(e -> {
      if (this.xAxis.getUpperBound() > 26) {
        this.mode = GraphMode.SIX_MONTHS;
        changeLowerBoundBasedOnMode();
      }
    });

    Button oneYearBtn = new Button("1Y");
    oneYearBtn.setOnAction(e -> {
      if (this.xAxis.getUpperBound() > 52) {
        this.mode = GraphMode.ONE_YEAR;
        changeLowerBoundBasedOnMode();
      }
    });

    Button maxBtn = new Button("MAX");
    maxBtn.setOnAction(e -> {
      this.mode = GraphMode.MAX;
      changeLowerBoundBasedOnMode();
    });

    HBox filters = new HBox();
    filters.getChildren().addAll(oneMonthBtn, threeMonthsBtn, sixMonthsBtn, oneYearBtn, maxBtn);
    return filters;
  }
}
