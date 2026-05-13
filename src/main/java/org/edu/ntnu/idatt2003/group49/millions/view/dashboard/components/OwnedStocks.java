package org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.MillionsTable;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.OwnedSharesTable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OwnedStocks extends MillionsView {
  private final Navigator navigator;
  private final MillionsTable<Share> table;

  public OwnedStocks(Navigator navigator, MillionsTable<Share> table) {
    this.navigator = navigator;
    this.table = table;
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

    table.setItems(List.of(
      new Share(
        new Stock("NVDA", "Nvidia", new BigDecimal("100")), new BigDecimal("200"), new BigDecimal("150")
      ),
      new Share(
        new Stock("APPL", "Apple", new BigDecimal("400")), new BigDecimal("123"), new BigDecimal("100")
      )
    ));

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
