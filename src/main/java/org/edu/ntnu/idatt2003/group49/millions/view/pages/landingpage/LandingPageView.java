package org.edu.ntnu.idatt2003.group49.millions.view.pages.landingpage;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.edu.ntnu.idatt2003.group49.millions.config.PreDefinedExchanges;
import org.edu.ntnu.idatt2003.group49.millions.controller.GameController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;
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
    startGameBtn.setOnAction(event -> body.getChildren().add(buildNewGameOverlay()));
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

    TextField customCapitalField = new TextField();
    customCapitalField.getStyleClass().add("landing-input");
    customCapitalField.setPromptText("custom amount");

    ToggleGroup capitalGroup = new ToggleGroup();
    HBox capitalChoices = createCapitalChoices(capitalGroup, customCapitalField);

    customCapitalField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.isBlank()) {
        capitalGroup.selectToggle(null);
        selectedStartingMoney = null;
      }
    });

    Label csvLabel = new Label("Choose exchange");
    csvLabel.getStyleClass().add("landing-label");

    Label selectedCSVLabel = new Label("No CSV file selected");
    selectedCSVLabel.getStyleClass().add("landing-label");

    ComboBox<String> csvDropdown = createPredefinedCSVDropdown(selectedCSVLabel);
    Button chooseCSVBtn = createChooseCsvButton(selectedCSVLabel, csvDropdown);

    HBox csvSelector = new HBox();
    csvSelector.getStyleClass().add("csv-selector");
    csvSelector.getChildren().addAll(csvDropdown, chooseCSVBtn);

    Label errorLabel = new Label();
    errorLabel.getStyleClass().add("landing-error");

    Button continueBtn = createMenuButton("Start Game");
    continueBtn.setOnAction(event -> handleStartGame(nameField, customCapitalField, errorLabel));

    form.getChildren().addAll(
            title,
            nameField,
            capitalLabel,
            capitalChoices,
            customCapitalField,
            csvLabel,
            csvSelector,
            selectedCSVLabel,
            errorLabel,
            continueBtn
    );

    errorLabel.setText("");
    overlay.getChildren().add(form);
    return overlay;
  }

  private ToggleButton createCapitalButton(String text, BigDecimal amount, ToggleGroup group, TextField customCapitalField) {
    ToggleButton button = new ToggleButton(text);
    button.getStyleClass().add("capital-button");
    button.setToggleGroup(group);

    button.setOnAction(event -> {
      if (button.isSelected()) {
        selectedStartingMoney = amount;
        customCapitalField.clear();
      }else {
       selectedStartingMoney = null;
      }
    });

    return button;
  }

  private HBox createCapitalChoices(ToggleGroup capitalGroup, TextField customCapitalField) {
    HBox capitalChoices = new HBox();
    capitalChoices.getStyleClass().add("capital-choices");

    ToggleButton capital1 = createCapitalButton("10 000", new BigDecimal("10000"), capitalGroup, customCapitalField);
    ToggleButton capital2 = createCapitalButton("100 000", new BigDecimal("100000"), capitalGroup, customCapitalField);
    ToggleButton capital3 = createCapitalButton("1 000 000", new BigDecimal("1000000"), capitalGroup, customCapitalField);

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

  private Button createChooseCsvButton(Label selectedCsvLabel, ComboBox<String> csvDropdown) {
    ImageView folderIcon = new ImageView(
            new Image(Objects.requireNonNull(getClass().getResource("/images/folder.png")).toExternalForm())
    );
    folderIcon.setFitWidth(24);
    folderIcon.setFitHeight(24);
    folderIcon.setPreserveRatio(true);

    Button button = new Button();
    button.setGraphic(folderIcon);
    button.getStyleClass().add("csv-icon-button");

    Tooltip tooltip = new Tooltip("Choose CSV file");
    tooltip.setShowDelay(Duration.millis(50));
    tooltip.setHideDelay(Duration.millis(100));
    button.setTooltip(tooltip);

    button.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose CSV file");
      fileChooser.getExtensionFilters().add(
              new FileChooser.ExtensionFilter("CSV files", "*.csv")
      );

      File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

      if (selectedFile != null) {
        csvDropdown.getSelectionModel().clearSelection();
        selectedCsvPath = selectedFile.toPath();
        selectedCsvLabel.setText("Selected: " + selectedFile.getName());
      }
    });

    return button;
  }

  private ComboBox<String> createPredefinedCSVDropdown(Label selectedCSVLabel) {
    ComboBox<String> csvDropdown = new ComboBox<>();
    csvDropdown.getItems().addAll(PreDefinedExchanges.FILES.keySet());
    csvDropdown.setPromptText("Select exchange");
    csvDropdown.getStyleClass().add("csv-dropdown");

    csvDropdown.setOnAction(event -> {
      String selectedCSV = csvDropdown.getValue();

      if (selectedCSV != null) {
        selectedCsvPath = PreDefinedExchanges.FILES.get(selectedCSV);
        selectedCSVLabel.setText("Selected: " + selectedCSV);
      }
    });

    return csvDropdown;
  }

  private void handleStartGame(
          TextField nameField,
          TextField customCapitalField,
          Label errorLabel
  ) {
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
      errorLabel.setText("Choose an exchange");
      return;
    }

    errorLabel.setText("");

    Optional<String> error = gameController.startNewGame(name, startingMoney, selectedCsvPath);

    if (error.isPresent()) {
      errorLabel.setText(error.get());
      return;
    }

    navigator.goToDashboard();
  }
}

