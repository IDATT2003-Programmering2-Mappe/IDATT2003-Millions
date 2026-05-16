package org.edu.ntnu.idatt2003.group49.millions.view.popups;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.PurchaseRequest;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;

public class BuySharePopup extends Popup {
  private final PlayerController playerController;

  private final Label errorLabel = new Label();
  private final TextField quantityField = new TextField();
  private final Button buyButton = new Button("Buy");
  private final Button cancelButton = new Button("Cancel");

  private Stock stock;
  private Consumer<PurchaseRequest> onBuy;

  public BuySharePopup(PlayerController playerController) {
    this.playerController = playerController;
    super();

    getPopupPane().setCenter(buildPopup());

    buyButton.setOnAction(event -> {
      int quantity = Integer.parseInt(quantityField.getText());

      PurchaseRequest request = new PurchaseRequest(stock.getSymbol(), BigDecimal.valueOf(quantity), playerController.getPlayer());

      if (onBuy != null) {
        onBuy.accept(request);
      }

      hide();
    });
  }

  private VBox buildPopup() {
    quantityField.setPromptText("Quantity");

    errorLabel.getStyleClass().add("error-label");
    errorLabel.setVisible(false);
    errorLabel.setManaged(false);

    buyButton.setDisable(true);

    quantityField.textProperty().addListener((obs, oldValue, newValue) -> validateInput());

    cancelButton.setOnAction(event -> hide());

    VBox popupBox = new VBox();
    popupBox.getChildren().addAll(
      quantityField,
      buyButton
    );

    return popupBox;
  }

  public void show(Stock stock, Consumer<PurchaseRequest> onBuy) {
    this.stock = Objects.requireNonNull(stock);
    this.onBuy = Objects.requireNonNull(onBuy);

    setTitle("Buy " + stock.getCompany() + " Shares");
    quantityField.clear();
    validateInput();

    setVisible(true);
    setManaged(true);
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
