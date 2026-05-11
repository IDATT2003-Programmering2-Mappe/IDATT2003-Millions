package org.edu.ntnu.idatt2003.group49.millions.view.landingpage;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;


public class LandingPageView extends MillionsView {
  private final Navigator navigator;

  public LandingPageView(Navigator navigator) {
    this.navigator = navigator;

    getStylesheets().add(Objects.requireNonNull(
            getClass().getResource("/styles/landingPage.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    StackPane body = new StackPane();
    body.getStyleClass().add("landing-body");

    VBox menu = new VBox();
    menu.getStyleClass().add("landing-menu");

    Button chooseCsvBtn = createMenuButton("Choose CSV file");
    Button startGameBtn = createMenuButton("Start Game");
    Button loadGameBtn = createMenuButton("Load Game");

    menu.getChildren().addAll(
            chooseCsvBtn,
            startGameBtn,
            loadGameBtn
    );

    body.getChildren().add(menu);
    return body;
  }

  private Button createMenuButton(String text) {
    Button button = new Button(text);
    button.getStyleClass().add("landing-button");
    return button;
  }
}
