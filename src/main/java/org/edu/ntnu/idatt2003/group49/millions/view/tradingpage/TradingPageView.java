package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage;

import javafx.scene.layout.*;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TableSelectionModel;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TradingTable;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory.StocksColumnFactory;
import org.edu.ntnu.idatt2003.group49.millions.view.dialogs.BuyStockPopup;
import org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components.StockInfo;

import java.util.List;
import java.util.Objects;

public class TradingPageView extends MillionsView {
  private final ExchangeController exchangeController;
  private final TradingTable tradingTable;
  private final StockInfo stockInfo;
  private final TableSelectionModel<Stock> selectionModel;

  public TradingPageView(ExchangeController exchangeController, TableSelectionModel<Stock> selectionModel) {
    this.exchangeController = exchangeController;
    this.selectionModel = selectionModel;
    BuyStockPopup buyStockPopup = new BuyStockPopup();
    this.tradingTable = new TradingTable(new StocksColumnFactory(buyStockPopup),  selectionModel);
    this.stockInfo = new StockInfo();

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/tradingpage.css")
    ).toExternalForm());
    getChildren().addAll(build(), buyStockPopup);
  }

  @Override
  protected Pane build() {
    selectionModel.selectedItemProperty().addListener((obs, oldStock, newStock) -> {
      stockInfo.setStock(newStock);
      stockInfo.updateStockInfo();
      System.out.println("updated from: " + oldStock + " to " + newStock);
    });

    List<Stock> stocks = exchangeController.getStockMap().values().stream().toList();
    tradingTable.setItems(stocks);

    stockInfo.updateStockInfo();

    // Outer TradingTable wrapper
    tradingTable.setMinHeight(0);
    tradingTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    // Inner actual TableView
    tradingTable.getTable().setMinHeight(0);
    tradingTable.getTable().setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    VBox leftBody = new VBox(tradingTable);
    leftBody.getStyleClass().add("left-body");

    leftBody.setMinHeight(0);
    leftBody.setMaxHeight(Double.MAX_VALUE);

    // Width behavior for left side
    leftBody.setMinWidth(500);
    leftBody.setPrefWidth(600);
    leftBody.setMaxWidth(800);

    VBox.setVgrow(tradingTable, Priority.ALWAYS);

    VBox rightBody = new VBox(stockInfo);
    rightBody.getStyleClass().add("right-body");

    rightBody.setMinHeight(0);
    rightBody.setMaxHeight(Double.MAX_VALUE);

    // Width behavior for right side
    rightBody.setMinWidth(400);
    rightBody.setPrefWidth(400);
    rightBody.setMaxWidth(Double.MAX_VALUE);

    HBox body = new HBox(leftBody, rightBody);
    body.getStyleClass().add("trading-page");
    body.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    // Let both participate in horizontal growth
    HBox.setHgrow(leftBody, Priority.ALWAYS);
    HBox.setHgrow(rightBody, Priority.ALWAYS);

    return body;
  }
}
