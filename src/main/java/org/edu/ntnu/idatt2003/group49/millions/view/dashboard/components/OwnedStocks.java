package org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

    Label ownedStocksLabel = new Label("Owned Stocks");
    ownedStocksLabel.getStyleClass().add("owned-stocks-label");

    HBox title = new HBox();
    title.getStyleClass().add("owned-stocks-title");
    title.getChildren().add(ownedStocksLabel);

    StocksTable table = new StocksTable(navigator);

    VBox vBox = new VBox();
    vBox.getStyleClass().add("owned-stocks");
    vBox.setSpacing(5);

    vBox.getChildren().addAll(
      title,
      table
    );
    return vBox;
  }
}
