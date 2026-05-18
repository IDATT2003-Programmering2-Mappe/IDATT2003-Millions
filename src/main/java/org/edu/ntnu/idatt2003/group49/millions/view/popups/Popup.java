package org.edu.ntnu.idatt2003.group49.millions.view.popups;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public abstract class Popup extends MillionsView {
  private final BorderPane popupPane = new BorderPane();
  private final Label title = new Label();
  private final Button exitButton = new Button("\uD83D\uDDD9");

  public Popup() {
    title.getStyleClass().add("title-label");
    exitButton.getStyleClass().addAll("button-reset", "exit-button");

    getStyleClass().add("popup-overlay");
    setVisible(false);
    setManaged(false);

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/popup.css")
    ).toExternalForm());

    exitButton.setOnAction(e -> {
      hide();
    });

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    popupPane.getStyleClass().add("buy-popup");

    popupPane.setMaxWidth(350);
    popupPane.setMaxHeight(300);

    popupPane.setTop(createPopupControls());

    return popupPane;
  }

  public void setResolution(int width, int height) {
    popupPane.setMaxWidth(width);
    popupPane.setMaxHeight(height);
  }

  protected void setTitle(String title) {
    this.title.setText(title);
  }

  protected BorderPane getPopupPane() {
    return popupPane;
  }

  private HBox createPopupControls() {
    HBox popupControls = new HBox();
    popupControls.getStyleClass().add("controls");

    popupControls.getChildren().addAll(
      title,
      exitButton
    );

    title.setMaxWidth(Double.MAX_VALUE);
    title.setMaxHeight(Double.MAX_VALUE);

    HBox.setHgrow(title, Priority.ALWAYS);

    return popupControls;
  }

  public void hide() {
    setVisible(false);
    setManaged(false);
  }
}
