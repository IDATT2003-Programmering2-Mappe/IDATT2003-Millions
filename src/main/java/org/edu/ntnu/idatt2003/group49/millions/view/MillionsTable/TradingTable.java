package org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory.StocksColumnFactory;

import java.util.List;

public class TradingTable extends MillionsTable<Stock> {
  private final StocksColumnFactory columnFactory;

  public TradingTable(StocksColumnFactory columnFactory, TableSelectionModel<Stock> selectionModel) {
    this.columnFactory = columnFactory;
    super();

    table.setRowFactory(_ -> {
      TableRow<Stock> row = new TableRow<>();

      row.setOnMouseClicked(e -> {
        if (!row.isEmpty()) {
          selectionModel.setSelectedItem(row.getItem());
        }
      });

      return row;
    });
  }

  @Override
  protected List<TableColumn<Stock, ?>> createColumns() {
    return List.of(
      columnFactory.createIndexColumn(getList()),
      columnFactory.createSymbolColumn(),
      columnFactory.createPriceColumn(),
      columnFactory.createChangeColumn(),
      columnFactory.createBuyColumn(),
      columnFactory.createSellColumn()
    );
  }
}
