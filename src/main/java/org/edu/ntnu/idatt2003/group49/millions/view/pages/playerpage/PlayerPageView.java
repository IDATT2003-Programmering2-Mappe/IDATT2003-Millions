package org.edu.ntnu.idatt2003.group49.millions.view.pages.playerpage;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class PlayerPageView extends MillionsView {
  private final PlayerController playerController;

  public PlayerPageView(PlayerController playerController) {
    this.playerController = Objects.requireNonNull(playerController, "playerController cannot be null");

    getStylesheets().add(Objects.requireNonNull(
            getClass().getResource("/styles/playerpage.css")
    ).toExternalForm());

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    HBox body = new HBox();
    body.getStyleClass().add("player-page");

    Pane archiveSection = createArchiveSection();
    VBox sidePanel = createSidePanel();

    HBox.setHgrow(archiveSection, Priority.ALWAYS);

    body.getChildren().addAll(archiveSection, sidePanel);
    return body;
  }

  private Pane createArchiveSection() {
    return new TransactionArchiveView(playerController);
  }

  private VBox createSidePanel() {
    VBox sidePanel = new VBox();
    sidePanel.getStyleClass().add("player-side-panel");

    sidePanel.setMinWidth(300);
    sidePanel.setPrefWidth(360);
    sidePanel.setMaxWidth(420);

    PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(playerController);

    sidePanel.getChildren().add(playerInfoPanel);
    return sidePanel;
  }
}
