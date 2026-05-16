package org.edu.ntnu.idatt2003.group49.millions.view.playerpage;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

public class PlayerPageView extends MillionsView {
  public PlayerPageView() {
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox body = new VBox();
    body.getStyleClass().add("player-page");

    // Midlertidig bare for å se at siden vises
    body.getChildren().add(new Label("Player Page"));

    return body;
  }
}
