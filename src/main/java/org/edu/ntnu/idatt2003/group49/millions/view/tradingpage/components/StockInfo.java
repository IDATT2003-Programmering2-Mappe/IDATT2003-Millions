package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;

public class StockInfo extends MillionsView {
  private final MillionsChart chart;

  public StockInfo(MillionsChart chart) {
    this.chart = chart;

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox body = new VBox();
    body.getChildren().addAll(
      new Label("Stock Info"),
      chart
    );
    return body;
  }
}
