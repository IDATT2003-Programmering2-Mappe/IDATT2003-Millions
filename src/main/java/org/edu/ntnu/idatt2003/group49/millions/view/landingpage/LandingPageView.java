package org.edu.ntnu.idatt2003.group49.millions.view.landingpage;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.math.BigDecimal;
import java.util.Objects;


public class LandingPageView extends MillionsView {
  private final Navigator navigator;
  private BigDecimal selectedStartingMoney;

  public LandingPageView(Navigator navigator) {
    this.navigator = navigator;

    getStylesheets().add(Objects.requireNonNull(
            getClass().getResource("/styles/landingPage.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    return buildMainMenu();
  }

  private Pane buildMainMenu() {
    StackPane body = new StackPane();
    body.getStyleClass().add("landing-body");

    VBox menu = new VBox();
    menu.getStyleClass().add("landing-menu");

    Button startGameBtn = createMenuButton("Start Game");
    startGameBtn.setOnAction(event -> getChildren().setAll(buildNewGameOverlay()));
    Button loadGameBtn = createMenuButton("Load Game");
    Button settingsBtn = createMenuButton("Settings");

    menu.getChildren().addAll(
            startGameBtn,
            loadGameBtn,
            settingsBtn
    );

    body.getChildren().add(menu);
    return body;
  }

  private Button createMenuButton(String text) {
    Button button = new Button(text);
    button.getStyleClass().add("landing-button");
    return button;
  }

  private Pane buildNewGameOverlay() {
    StackPane overlay = new StackPane();
    overlay.getStyleClass().add("landing-overlay");

    VBox form = new VBox();
    form.getStyleClass().add("new-game-form");

    Label title = new Label("New Game");
    title.getStyleClass().add("landing-title");

    TextField nameField = new TextField();
    nameField.getStyleClass().add("landing-input");
    nameField.setPromptText("Name");

    Label capitalLabel = new Label("Start capital");
    capitalLabel.getStyleClass().add("landing-label");

    HBox capitalChoices = new HBox();
    capitalChoices.getStyleClass().add("capital-choices");

    ToggleGroup capitalGroup = new ToggleGroup();

    ToggleButton capital1 = createCapitalButton("10 000", new BigDecimal("10000"), capitalGroup);
    ToggleButton capital2 = createCapitalButton("100 000", new BigDecimal("100000"), capitalGroup);
    ToggleButton capital3 = createCapitalButton("1 000 000", new BigDecimal("1000000"),capitalGroup);

    capitalChoices.getChildren().addAll(
            capital1,
            capital2,
            capital3
    );

    TextField customCapitalField = new TextField();
    customCapitalField.getStyleClass().add("landing-input");
    customCapitalField.setPromptText("custom amount");

    Button continueBtn = createMenuButton("Continue");

    form.getChildren().addAll(
            title,
            nameField,
            capitalLabel,
            capitalChoices,
            customCapitalField,
            continueBtn
    );

    overlay.getChildren().add(form);
    return overlay;
  }

  private ToggleButton createCapitalButton(String text, BigDecimal amount, ToggleGroup group) {
    ToggleButton button = new ToggleButton(text);
    button.getStyleClass().add("capital-button");
    button.setToggleGroup(group);

    button.setOnAction(event -> selectedStartingMoney = amount);

    return button;
  }
}
