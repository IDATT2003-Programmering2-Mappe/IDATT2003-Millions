package org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.Table.StocksTable;

import java.util.Objects;

public class OwnedStocks extends MillionsView {
  private final Navigator navigator;

  public OwnedStocks(Navigator navigator) {
    this.navigator = navigator;
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox vBox = new VBox();
    StocksTable table = new StocksTable(navigator);
    vBox.getChildren().add(table);
    return vBox;
  }
}
