package org.edu.ntnu.idatt2003.group49.millions.view.playerpage.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;

import java.util.Objects;

public class PlayerInfoPanel extends VBox {
  private final PlayerController playerController;

  public PlayerInfoPanel(PlayerController playerController) {
    this.playerController = Objects.requireNonNull(playerController, "playerController cannot be null");

    getStyleClass().add("player-info-panel");
    getChildren().addAll(
            createTitle(),
            createInfoRow("Name", playerController.getName()),
            createInfoRow("Portfolio", "$" + playerController.getPortfolioValue())
    );
  }

  private Label createTitle() {
    Label title = new Label("Player Info");
    title.getStyleClass().add("section-title");
    title.setMaxWidth(Double.MAX_VALUE);
    title.setAlignment(Pos.CENTER);
    return title;
  }

  private HBox createInfoRow(String labelText, String valueText) {
    HBox row = new HBox();
    row.getStyleClass().add("info-row");

    Label label = new Label(labelText);
    label.getStyleClass().add("info-label");

    Label value = new Label(valueText);
    value.getStyleClass().add("info-value");

    row.getChildren().addAll(label, value);
    return row;
  }
}
