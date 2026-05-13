package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TradingTable;
import org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components.StockInfo;

import java.util.List;

public class TradingPageView extends MillionsView {
  private final ExchangeController exchangeController;
  private final TradingTable tradingTable;
  private final MillionsChart chart;

  public TradingPageView(ExchangeController exchangeController, TradingTable tradingTable, MillionsChart chart) {
    this.exchangeController = exchangeController;
    this.tradingTable = tradingTable;
    this.chart = chart;

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    BorderPane body = new BorderPane();
    body.setLeft(tradingTable);
    List<Stock> stocks = exchangeController.getStockMap().values().stream().toList();
    tradingTable.setItems(stocks);
    body.setRight(new StockInfo(chart));
    return body;
  }
}
