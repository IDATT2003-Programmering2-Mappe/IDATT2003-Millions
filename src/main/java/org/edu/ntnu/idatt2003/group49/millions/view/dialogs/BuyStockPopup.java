package org.edu.ntnu.idatt2003.group49.millions.view.dialogs;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.PurchaseRequest;

import java.util.Objects;
import java.util.function.Consumer;

public class BuyStockPopup extends StackPane {
  private final TextField quantityField = new TextField();
  private final Button buyButton = new Button("Buy");
  private final Button cancelButton = new Button("Cancel");
  private final Label errorLabel = new Label();

  private Stock stock;
  private Consumer<PurchaseRequest> onBuy;

  public BuyStockPopup() {
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/popup.css")
    ).toExternalForm());
    getStyleClass().add("popup-overlay");
    setVisible(false);
    setManaged(false);

    VBox popupBox = new VBox(12);
    popupBox.getStyleClass().add("buy-popup");

    Label titleLabel = new Label("Buy Stock");

    quantityField.setPromptText("Quantity");

    errorLabel.getStyleClass().add("error-label");
    errorLabel.setVisible(false);
    errorLabel.setManaged(false);

    buyButton.setDisable(true);

    quantityField.textProperty().addListener((obs, oldValue, newValue) -> validateInput());

    buyButton.setOnAction(event -> {
      int quantity = Integer.parseInt(quantityField.getText());

      PurchaseRequest request = new PurchaseRequest(stock, quantity);

      if (onBuy != null) {
        onBuy.accept(request);
      }

      hide();
    });

    cancelButton.setOnAction(event -> hide());

    popupBox.getChildren().addAll(
      titleLabel,
      quantityField,
      errorLabel,
      new HBox(10, cancelButton, buyButton)
    );

    getChildren().add(popupBox);
  }

  public void show(Stock stock, Consumer<PurchaseRequest> onBuy) {
    this.stock = Objects.requireNonNull(stock);
    this.onBuy = Objects.requireNonNull(onBuy);

    quantityField.clear();
    validateInput();

    setVisible(true);
    setManaged(true);
  }

  public void hide() {
    setVisible(false);
    setManaged(false);
  }

  private void validateInput() {
    try {
      int quantity = Integer.parseInt(quantityField.getText());

      if (quantity <= 0) {
        showError("Quantity must be greater than 0");
        buyButton.setDisable(true);
        return;
      }

      hideError();
      buyButton.setDisable(false);

    } catch (NumberFormatException e) {
      showError("Quantity must be a whole number");
      buyButton.setDisable(true);
    }
  }

  private void showError(String message) {
    errorLabel.setText(message);
    errorLabel.setVisible(true);
    errorLabel.setManaged(true);
  }

  private void hideError() {
    errorLabel.setVisible(false);
    errorLabel.setManaged(false);
  }
}
