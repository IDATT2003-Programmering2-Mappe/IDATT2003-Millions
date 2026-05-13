package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory.StocksColumnFactory;

import java.util.List;

public class TradingTable extends MillionsTable<Stock> {
  private final StocksColumnFactory columnFactory;

  public TradingTable(Navigator navigator, StocksColumnFactory columnFactory) {
    this.columnFactory = columnFactory;
    super(navigator);
  }

  @Override
  protected List<TableColumn<Stock, ?>> createColumns() {
    return List.of(
      columnFactory.createSymbolColumn(),
      columnFactory.createPriceColumn(),
      columnFactory.createChangeColumn()
    );
  }
}
