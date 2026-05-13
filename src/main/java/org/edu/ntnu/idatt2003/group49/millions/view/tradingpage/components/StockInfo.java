package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

public class StockInfo extends MillionsView {
  public StockInfo() {
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox body = new VBox();
    body.getChildren().add(new Label("Stock Info"));
    return body;
  }
}
