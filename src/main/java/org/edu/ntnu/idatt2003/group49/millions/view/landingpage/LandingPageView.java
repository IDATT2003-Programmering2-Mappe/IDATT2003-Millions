package org.edu.ntnu.idatt2003.group49.millions.view.landingpage;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.edu.ntnu.idatt2003.group49.millions.controller.GameController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;


public class LandingPageView extends MillionsView {
  private final Navigator navigator;
  private final GameController gameController;
  private BigDecimal selectedStartingMoney;
  private Path selectedCsvPath;

  public LandingPageView(Navigator navigator, GameController gameController) {
    this.navigator = navigator;
    this.gameController = gameController;

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

    ToggleGroup capitalGroup = new ToggleGroup();
    HBox capitalChoices = createCapitalChoices(capitalGroup);

    TextField customCapitalField = new TextField();
    customCapitalField.getStyleClass().add("landing-input");
    customCapitalField.setPromptText("custom amount");

    Button chooseCsvBtn = createMenuButton("Choose CSV file");
    Label selectedCsvLabel = new Label("No CSV file selected");
    selectedCsvLabel.getStyleClass().add("landing-label");
    chooseCsvBtn.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose CSV file");
      fileChooser.getExtensionFilters().add(
              new FileChooser.ExtensionFilter("CSV files", "*.csv")
      );

      File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
      if (selectedFile != null) {
        selectedCsvPath = selectedFile.toPath();
        selectedCsvLabel.setText(selectedFile.getAbsolutePath());
      }
    });

    Label errorLabel = new Label();
    errorLabel.getStyleClass().add("landing-error");

    Button continueBtn = createMenuButton("Continue");
    continueBtn.setOnAction(event -> {
      String name = nameField.getText().trim();

      if (name.isBlank()) {
        errorLabel.setText("Name cannot be empty");
        return;
      }

      BigDecimal startingMoney = getStartingMoney(customCapitalField);

      if (startingMoney == null) {
        errorLabel.setText("Choose a start capital or enter a custom amount");
        return;
      }

      if (startingMoney.compareTo(BigDecimal.ZERO) <= 0) {
        errorLabel.setText("Start capital must be greater than zero");
        return;
      }

      if (selectedCsvPath == null) {
        errorLabel.setText("Choose a CSV file");
        return;
      }

      errorLabel.setText("");

      Optional<String> error = gameController.startNewGame(name, startingMoney, selectedCsvPath);
      if (error.isPresent()) {
        errorLabel.setText(error.get());
        return;
      }
      navigator.goToDashboard();
    });

    form.getChildren().addAll(
            title,
            nameField,
            capitalLabel,
            capitalChoices,
            customCapitalField,
            chooseCsvBtn,
            selectedCsvLabel,
            errorLabel,
            continueBtn
    );

    errorLabel.setText("");
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

  private HBox createCapitalChoices(ToggleGroup capitalGroup) {
    HBox capitalChoices = new HBox();
    capitalChoices.getStyleClass().add("capital-choices");

    ToggleButton capital1 = createCapitalButton("10 000", new BigDecimal("10000"), capitalGroup);
    ToggleButton capital2 = createCapitalButton("100 000", new BigDecimal("100000"), capitalGroup);
    ToggleButton capital3 = createCapitalButton("1 000 000", new BigDecimal("1000000"), capitalGroup);

    capitalChoices.getChildren().addAll(
            capital1,
            capital2,
            capital3
    );

    return capitalChoices;
  }

  private BigDecimal getStartingMoney(TextField customCapitalField) {
    String customAmount = customCapitalField.getText().trim();

    if (!customAmount.isBlank()) {
      try {
        return new BigDecimal(customAmount);
      } catch (NumberFormatException e) {
        return null;
      }
    }

    return selectedStartingMoney;
  }
}
