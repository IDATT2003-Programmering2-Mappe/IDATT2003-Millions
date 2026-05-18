package org.edu.ntnu.idatt2003.group49.millions.view.popups;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.PurchaseRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.Consumer;

public class BuySharePopup extends Popup {
  private final PlayerController playerController;

  private final Label errorLabel = new Label();
  private final TextField quantityField = new TextField();
  private final Button buyButton = new Button("Buy");

  private final Label symbolLabel = new Label("Milli");
  private final Label cashLabel = new Label("0");
  private final Label priceLabel = new Label("0");
  private final Label totalLabel = new Label("0");

  private Stock stock;
  private Consumer<PurchaseRequest> onBuy;
  private BigDecimal price = new BigDecimal("1");

  public BuySharePopup(PlayerController playerController) {
    this.playerController = playerController;
    super();

    getPopupPane().setCenter(buildPopup());

    quantityField.getStyleClass().add("quantity-field");
    errorLabel.getStyleClass().add("error-label");

    buyButton.getStyleClass().addAll("button-reset", "buy-button");
    buyButton.setMaxHeight(Double.MAX_VALUE);
    buyButton.setMaxWidth(Double.MAX_VALUE);

    buyButton.setOnAction(event -> {
      BigDecimal quantity = new BigDecimal(quantityField.getText());

      PurchaseRequest request = new PurchaseRequest(stock.getSymbol(), quantity, playerController.getPlayer());

      if (onBuy != null) {
        onBuy.accept(request);
      }

      hide();
    });
  }

  private VBox buildPopup() {
    HBox infoBox = createInfoBox();
    HBox spacerBox = new HBox();
    VBox inputBox = createInputBox();
    VBox totalBox = createTotalBox();
    HBox buyBox = new HBox(buyButton);

    infoBox.getStyleClass().add("info-box");
    inputBox.getStyleClass().add("input-box");
    totalBox.getStyleClass().add("purchase-box");
    buyBox.getStyleClass().add("buy-box");

    infoBox.setMaxWidth(Double.MAX_VALUE);
    spacerBox.setMaxHeight(Double.MAX_VALUE);
    inputBox.setMaxWidth(Double.MAX_VALUE);
    buyBox.setMaxWidth(Double.MAX_VALUE);

    HBox.setHgrow(inputBox, Priority.ALWAYS);
    HBox.setHgrow(infoBox, Priority.ALWAYS);
    HBox.setHgrow(buyButton, Priority.ALWAYS);

    VBox content = new VBox(infoBox, spacerBox, inputBox, totalBox, buyBox);
    content.getStyleClass().add("content");

    content.setSpacing(10);

    VBox.setVgrow(spacerBox, Priority.ALWAYS);

    return content;
  }

  public void show(Stock stock, Consumer<PurchaseRequest> onBuy) {
    this.stock = Objects.requireNonNull(stock);
    this.onBuy = Objects.requireNonNull(onBuy);
    this.price = stock.getSalesPrice();

    setTitle("Buy " + stock.getCompany() + " Shares");
    quantityField.clear();

    symbolLabel.setText(stock.getSymbol());
    cashLabel.setText("$" + playerController.getMoney().toEngineeringString());
    priceLabel.setText("$" + price.toEngineeringString());

    validateInput();

    setVisible(true);
    setManaged(true);
  }

  private HBox createInfoBox() {
    Label idSymbolLabel = new Label("Symbol:");
    Label idCashLabel = new Label("Cash:");
    Label idPriceLabel = new Label("Price:");

    VBox identifiers = new VBox(idSymbolLabel, idCashLabel, idPriceLabel);

    VBox info = new VBox(symbolLabel, cashLabel, priceLabel);

    return new HBox(identifiers, info);
  }

  private VBox createInputBox() {
    quantityField.setPromptText("Quantity");

    errorLabel.getStyleClass().addAll("button-reset", "error-label");
    errorLabel.setVisible(false);
    errorLabel.setManaged(false);

    buyButton.setDisable(true);

    quantityField.textProperty().addListener((obs, oldValue, newValue) -> validateInput());

    return new VBox(quantityField, errorLabel);
  }

  private VBox createTotalBox() {
    Label idTotalLabel = new Label("Total:");

    HBox totalBox = new HBox(idTotalLabel, totalLabel);

    totalBox.getStyleClass().add("total-box");
    totalBox.setMaxWidth(Double.MAX_VALUE);
    totalBox.setSpacing(15);

    HBox.setHgrow(totalBox, Priority.ALWAYS);

    return new VBox(totalBox);
  }

  private BigDecimal calculateTotal(BigDecimal quantity) {
    return price.multiply(quantity).setScale(3, RoundingMode.HALF_UP);
  }

  private void validateInput() {
    if (quantityField.getText().isBlank()) {
      buyButton.setDisable(true);
      totalLabel.setText("0");
      hideError();
      return;
    }

    try {
      BigDecimal quantity = new BigDecimal(quantityField.getText());
      totalLabel.setText("$" + calculateTotal(quantity).toEngineeringString());

      if (quantity.signum() <= 0) {
        showError("Quantity must be greater than 0");
        buyButton.setDisable(true);
        return;
      }

      if (playerController.getMoney().compareTo(calculateTotal(quantity)) < 0) {
        showError("Insufficient funds");
        buyButton.setDisable(true);
        return;
      }

      hideError();
      buyButton.setDisable(false);

    } catch (NumberFormatException e) {
      showError("Quantity must be a number");
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
    errorLabel.setManaged(true);
  }
}
