package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TradingTable;
import org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components.StockInfo;

public class TradingPageView extends MillionsView {
  private final TradingTable tradingTable;

  public TradingPageView(TradingTable tradingTable) {
    this.tradingTable = tradingTable;

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    BorderPane body = new BorderPane();
    body.setLeft(tradingTable);
    body.setRight(new StockInfo());
    return body;
  }
}
