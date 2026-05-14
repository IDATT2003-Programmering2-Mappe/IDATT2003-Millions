package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TradingTable;
import org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components.StockInfo;

import java.util.List;
import java.util.Objects;

public class TradingPageView extends MillionsView {
  private final ExchangeController exchangeController;
  private final TradingTable tradingTable;
  private final StockInfo stockInfo;

  public TradingPageView(ExchangeController exchangeController, TradingTable tradingTable, StockInfo stockInfo) {
    this.exchangeController = exchangeController;
    this.tradingTable = tradingTable;
    this.stockInfo = stockInfo;

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/tradingpage.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    List<Stock> stocks = exchangeController.getStockMap().values().stream().toList();
    tradingTable.setItems(stocks);

    stockInfo.setStock(exchangeController.getStock("NVDA"));
    stockInfo.updateStockInfo();

    // Outer TradingTable wrapper
    tradingTable.setMinHeight(0);
    tradingTable.setMaxHeight(Double.MAX_VALUE);
    tradingTable.setMaxWidth(Double.MAX_VALUE);

    // Inner actual TableView
    tradingTable.getTable().setMinHeight(0);
    tradingTable.getTable().setMaxHeight(Double.MAX_VALUE);
    tradingTable.getTable().setMaxWidth(Double.MAX_VALUE);

    VBox leftBody = new VBox(tradingTable);
    leftBody.getStyleClass().add("left-body");
    leftBody.setMinHeight(0);
    leftBody.setMaxHeight(Double.MAX_VALUE);

    VBox.setVgrow(tradingTable, Priority.ALWAYS);

    VBox rightBody = new VBox(stockInfo);
    rightBody.getStyleClass().add("right-body");
    rightBody.setMaxHeight(Double.MAX_VALUE);

    HBox body = new HBox(leftBody, rightBody);
    body.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    HBox.setHgrow(leftBody, Priority.ALWAYS);

    return body;
  }
}
