package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart;

import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
  private ChartMode chartMode = ChartMode.MAX;

  private String name;
  private BigDecimal value;
  private BigDecimal change;

  private Label nameLabel;
  private Label valueLabel;
  private Label changeLabel;

  private List<Button> filterButtons;

  public MillionsChart(String name, BigDecimal value, BigDecimal change) {
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/chart.css")
    ).toExternalForm());

    this.series = new XYChart.Series<>();
    this.name = name;
    this.value = value;
    this.change = change;

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    return createChartContainer();
  }

  private VBox createChartContainer() {
    VBox chartContainer = new VBox();
    chartContainer.getStyleClass().add("chart-container");
    chartContainer.getChildren().addAll(
      createInfoBar(),
      filters(),
      createChart()
    );

    return chartContainer;
  }

  private BorderPane createInfoBar() {
    nameLabel = new Label(name);
    valueLabel = new Label(value.toString());
    changeLabel = new Label(change.toString() + "%");

    nameLabel.getStyleClass().add("name");
    valueLabel.getStyleClass().add("price");
    changeLabel.getStyleClass().add("change");

    updateChange();

    HBox valueSection = new HBox();
    valueSection.getStyleClass().add("value-section");
    valueSection.getChildren().addAll(
      valueLabel,
      changeLabel
    );

    BorderPane infoBar = new BorderPane();
    infoBar.getStyleClass().add("info-bar");
    infoBar.setLeft(nameLabel);
    infoBar.setRight(valueSection);

    return infoBar;
  }

  public void updateInfoBar(String name, BigDecimal value, BigDecimal change) {
    this.name = name;
    this.value = value;
    this.change = change;

    nameLabel.setText(name);
    valueLabel.setText(value.toString());
    changeLabel.setText(change + "%");

    updateChange();
  }

  private void updateChange() {
    changeLabel.getStyleClass().removeAll("positive-change", "negative-change", "zero-change");

    if (change.signum() > 0) {
      changeLabel.setText("+" + change + "%");
      changeLabel.getStyleClass().add("positive-change");
    }
    else if (change.signum() < 0) {
      changeLabel.setText(change + "%");
      changeLabel.getStyleClass().add("negative-change");
    }
    else {
      changeLabel.setText(change + "%");
      changeLabel.getStyleClass().add("zero-change");
    }
  }

  private AreaChart<Number, Number> createChart() {
    this.xAxis = new NumberAxis(0, 1, 1);

    NumberAxis yAxis = new NumberAxis();
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
        xAxis.setLowerBound(0);
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
