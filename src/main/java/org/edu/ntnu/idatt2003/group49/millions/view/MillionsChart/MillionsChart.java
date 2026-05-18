package org.edu.ntnu.idatt2003.group49.millions.view.MillionsChart;

import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.edu.ntnu.idatt2003.group49.millions.config.TimeConstants;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class MillionsChart extends MillionsView {
  private final Logger logger = Logger.getLogger(MillionsChart.class.getName());
  private AreaChart<Number, Number> chart;
  private final XYChart.Series<Number, Number> series;
  private NumberAxis xAxis;
  private NumberAxis yAxis;
  private ChartMode chartMode = ChartMode.MAX;

  private List<Button> filterButtons;

  public MillionsChart() {
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/chart.css")
    ).toExternalForm());

    this.series = new XYChart.Series<>();

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    return createChartContainer();
  }

  private VBox createChartContainer() {
    VBox chartContainer = new VBox(filters(), createChart());
    chartContainer.getStyleClass().add("chart-container");

    return chartContainer;
  }

  private AreaChart<Number, Number> createChart() {
    this.xAxis = new NumberAxis(1, 2, 1);

    this.yAxis = new NumberAxis();
    yAxis.setSide(Side.RIGHT);

    yAxis.setTickLabelFormatter(new StringConverter<Number>() {
      @Override
      public String toString(Number value) {
        return "$" + String.format("%.0f", value.doubleValue());
      }

      @Override
      public Number fromString(String string) {
        return Double.parseDouble(string.replace("%", ""));
      }
    });

    this.chart = new AreaChart<>(xAxis, yAxis);
    chart.setAnimated(false);
    chart.setCreateSymbols(false);
    chart.setLegendVisible(false);

    chart.getData().add(series);

    updateChartBasedOnMode();

    return chart;
  }

  public void addData(Number x, Number y) {
    XYChart.Data<Number, Number> data = new XYChart.Data<>(x, y);

    this.series.getData().add(data);

    double xValue = x.doubleValue();
    xAxis.setUpperBound(xValue);

    updateChartBasedOnMode();
  }

  public void clearData() {
    this.series.getData().clear();
  }

  public void updateYAxis(BigDecimal highestPrice, BigDecimal lowestPrice) {
    yAxis.setAutoRanging(false);

    double high = highestPrice.doubleValue();
    double low = lowestPrice.doubleValue();

    double range = high - low;

    // Normal padding
    double padding = Math.max(range * 0.05, 1);

    double rawLowerBound = low - padding;
    double rawUpperBound = high + padding;

    // Tick unit based on padded range
    double tickUnit = calculateRoundTickUnit(rawUpperBound - rawLowerBound, 5);

    // Round outward, not inward
    double lowerBound = Math.floor(rawLowerBound / tickUnit) * tickUnit;
    double upperBound = Math.ceil(rawUpperBound / tickUnit) * tickUnit;

    yAxis.setLowerBound(lowerBound);
    yAxis.setUpperBound(upperBound);
    yAxis.setTickUnit(tickUnit);
  }

  private double calculateRoundTickUnit(double range, int targetTickCount) {
    if (range <= 0) {
      return 1;
    }

    double roughTickUnit = Math.ceil(range / targetTickCount);

    double exponent = Math.floor(Math.log10(roughTickUnit));
    double base = Math.pow(10, exponent);
    double fraction = roughTickUnit / base;

    double niceFraction;

    if (fraction <= 1) {
      niceFraction = 1;
    } else if (fraction <= 2) {
      niceFraction = 2;
    } else if (fraction <= 5) {
      niceFraction = 5;
    } else {
      niceFraction = 10;
    }

    return niceFraction * base;
  }

  private void updateChartBasedOnMode() {
    switch (chartMode) {
      case ONE_MONTH -> {
        if (this.xAxis.getUpperBound() > TimeConstants.ONE_MONTH_IN_WEEKS) {
          xAxis.setLowerBound(xAxis.getUpperBound() - TimeConstants.ONE_MONTH_IN_WEEKS);
        }
        xAxis.setTickUnit(1);
      }
      case THREE_MONTHS -> {
        if (this.xAxis.getUpperBound() > TimeConstants.THREE_MONTHS_IN_WEEKS) {
          xAxis.setLowerBound(xAxis.getUpperBound() - TimeConstants.THREE_MONTHS_IN_WEEKS);
        }
        xAxis.setTickUnit(1);
      }
      case SIX_MONTHS -> {
        if (this.xAxis.getUpperBound() > TimeConstants.SIX_MONTHS_IN_WEEKS) {
          xAxis.setLowerBound(xAxis.getUpperBound() - TimeConstants.SIX_MONTHS_IN_WEEKS);
        }
        xAxis.setTickUnit(Math.round((double) TimeConstants.SIX_MONTHS_IN_WEEKS / 10));
      }
      case ONE_YEAR -> {
        if (this.xAxis.getUpperBound() > TimeConstants.ONE_YEAR_IN_WEEKS) {
          xAxis.setLowerBound(xAxis.getUpperBound() - TimeConstants.ONE_YEAR_IN_WEEKS);
        }
        xAxis.setTickUnit(Math.round((double) TimeConstants.ONE_YEAR_IN_WEEKS / 10));
      }
      case MAX -> {
        xAxis.setLowerBound(1);
        if (xAxis.getUpperBound() >= TimeConstants.THREE_MONTHS_IN_WEEKS) {
          xAxis.setTickUnit(Math.round(xAxis.getUpperBound() / 10));
        }
      }
    }
  }

  private HBox filters() {
    Button oneMonthBtn = createFilterButton("1M", ChartMode.ONE_MONTH);
    Button threeMonthsBtn = createFilterButton("3M", ChartMode.THREE_MONTHS);
    Button sixMonthsBtn = createFilterButton("6M", ChartMode.SIX_MONTHS);
    Button oneYearBtn = createFilterButton("1Y", ChartMode.ONE_YEAR);
    Button maxBtn = createFilterButton("MAX", ChartMode.MAX);

    this.filterButtons = List.of(oneMonthBtn, threeMonthsBtn, sixMonthsBtn, oneYearBtn, maxBtn);

    filterButtons.forEach(b -> b.setId("filter-btn"));
    maxBtn.setId("filter-btn-clicked");

    HBox filters = new HBox();
    filters.setSpacing(4);
    filters.getStyleClass().add("filters");
    filters.getChildren().addAll(filterButtons);

    return filters;
  }

  private void handleFilterButtonClick(ChartMode mode) {
    this.chartMode = mode;
    updateChartBasedOnMode();
    logger.info("Switched chart mode to: " + this.chartMode);
  }

  private Button createFilterButton(String name, ChartMode mode) {
    Button filterBtn = new Button(name);

    filterBtn.setOnAction(e -> {
      handleFilterButtonClick(mode);

      filterButtons.forEach(b -> b.setId("filter-btn"));
      filterBtn.setId("filter-btn-clicked");
    });

    return filterBtn;
  }
}
