package org.edu.ntnu.idatt2003.group49.millions.view.pages.dashboard;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.MillionsTable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OwnedShares extends MillionsView {
  private final PlayerController playerController;
  private final MillionsTable<Share> table;

  public OwnedShares(PlayerController playerController, MillionsTable<Share> table) {
    this.playerController = playerController;
    this.table = table;

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    Label ownedSharesLabel = new Label("Owned Shares");
    ownedSharesLabel.getStyleClass().add("owned-shares-label");

    HBox title = new HBox(ownedSharesLabel);
    title.getStyleClass().add("owned-shares-title");

    table.setItems(playerController.getOwnedShares());

    VBox vBox = new VBox(title, table);
    vBox.getStyleClass().add("owned-shares");
    vBox.setSpacing(5);

    return vBox;
  }
}
