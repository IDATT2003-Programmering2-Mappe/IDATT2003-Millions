package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsGraph;

import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class MillionsChart extends MillionsView {
  private Logger logger = Logger.getLogger(MillionsChart.class.getName());
  private final XYChart.Series<Number, Number> series;
  private NumberAxis xAxis;
  private NumberAxis yAxis;
  private ChartMode chartMode = ChartMode.MAX;
  private final List<Button> filterButtons = new ArrayList<>();

  public MillionsChart() {
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/chart.css")
    ).toExternalForm());

    this.series = new XYChart.Series<>();
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    this.xAxis = new NumberAxis(1, 2, 1);
    this.yAxis = new NumberAxis();

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

    AreaChart<Number, Number> chart = new AreaChart<>(xAxis, yAxis);
    chart.setAnimated(false);
    chart.setCreateSymbols(false);
    chart.setLegendVisible(false);

    chart.getData().add(series);

    VBox chartContainer = new VBox();
    chartContainer.getStyleClass().add("chart-container");
    chartContainer.getChildren().addAll(
      filters(),
      chart
    );
    return chartContainer;
  }

  public void addData(XYChart.Data<Number, Number> data) {
    xAxis.setUpperBound(data.getXValue().doubleValue());
    updateChartBasedOnMode();
    this.series.getData().add(data);
  }

  public void clearData() {
    this.series.getData().clear();
  }

  private void updateChartBasedOnMode() {
    int oneMonthInWeeks = 5;
    int threeMonthsInWeeks = 13;
    int sixMonthsInWeeks = 26;
    int oneYearInWeeks = 52;

    switch (chartMode) {
      case ONE_MONTH -> {
        if (this.xAxis.getUpperBound() > oneMonthInWeeks) {
          xAxis.setLowerBound(xAxis.getUpperBound() - oneMonthInWeeks);
        }
        xAxis.setTickUnit(1);
      }
      case THREE_MONTHS -> {
        if (this.xAxis.getUpperBound() > threeMonthsInWeeks) {
          xAxis.setLowerBound(xAxis.getUpperBound() - threeMonthsInWeeks);
        }
        xAxis.setTickUnit(1);
      }
      case SIX_MONTHS -> {
        if (this.xAxis.getUpperBound() > sixMonthsInWeeks) {
          xAxis.setLowerBound(xAxis.getUpperBound() - sixMonthsInWeeks);
        }
        xAxis.setTickUnit(Math.round((double) sixMonthsInWeeks / 10));
      }
      case ONE_YEAR -> {
        if (this.xAxis.getUpperBound() > oneYearInWeeks) {
          xAxis.setLowerBound(xAxis.getUpperBound() - oneYearInWeeks);
        }
        xAxis.setTickUnit(Math.round((double) oneYearInWeeks / 10));
      }
      case MAX -> {
        xAxis.setLowerBound(1);
        if (xAxis.getUpperBound() >= threeMonthsInWeeks) {
          xAxis.setTickUnit(Math.round(xAxis.getUpperBound() / 10));
        }
      }
    }
  }

  private void handleFilterButtonClick(ChartMode mode) {
    this.chartMode = mode;
    updateChartBasedOnMode();
    resetFilterButtonsStyles();
    logger.info("Changed chart mode to: " + this.chartMode);
  }

  private void resetFilterButtonsStyles() {
    this.filterButtons.forEach(b -> b.setId("filters-btn"));
  }

  private HBox filters() {
    Button oneMonthBtn = new Button("1M");
    oneMonthBtn.setId("filters-btn");
    oneMonthBtn.setOnAction(e -> {
      handleFilterButtonClick(ChartMode.ONE_MONTH);
      oneMonthBtn.setId("filter-btn-clicked");
    });

    Button threeMonthsBtn = new Button("3M");
    threeMonthsBtn.setId("filters-btn");
    threeMonthsBtn.setOnAction(e -> {
      handleFilterButtonClick(ChartMode.THREE_MONTHS);
      threeMonthsBtn.setId("filter-btn-clicked");
    });

    Button sixMonthsBtn = new Button("6M");
    sixMonthsBtn.setId("filters-btn");
    sixMonthsBtn.setOnAction(e -> {
      handleFilterButtonClick(ChartMode.SIX_MONTHS);
      sixMonthsBtn.setId("filter-btn-clicked");
    });

    Button oneYearBtn = new Button("1Y");
    oneYearBtn.setId("filters-btn");
    oneYearBtn.setOnAction(e -> {
      handleFilterButtonClick(ChartMode.ONE_YEAR);
      oneYearBtn.setId("filter-btn-clicked");
    });

    Button maxBtn = new Button("MAX");
    maxBtn.setId("filters-btn");
    maxBtn.setOnAction(e -> {
      handleFilterButtonClick(ChartMode.MAX);
      maxBtn.setId("filter-btn-clicked");
    });

    this.filterButtons.addAll(
      List.of(oneMonthBtn, threeMonthsBtn, sixMonthsBtn, oneYearBtn, maxBtn)
    );

    HBox filters = new HBox();
    filters.setSpacing(4);
    filters.getStyleClass().add("filters");
    filters.getChildren().addAll(filterButtons);
    return filters;
  }
}
