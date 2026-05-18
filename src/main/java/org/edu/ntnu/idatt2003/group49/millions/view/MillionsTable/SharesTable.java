package org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable;

import javafx.scene.control.*;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory.SharesColumnFactory;

import java.util.List;

public class SharesTable extends MillionsTable<Share> {
  private final SharesColumnFactory columnFactory;

  public SharesTable(SharesColumnFactory columnFactory, TableSelectionModel<Share> selectionModel) {
    this.columnFactory = columnFactory;
    super();

    table.setRowFactory(_ -> {
      TableRow<Share> row = new TableRow<>();

      row.setOnMouseClicked(e -> {
        if (!row.isEmpty()) {
          selectionModel.setSelectedItem(row.getItem());
        }
      });

      return row;
    });
  }

  protected List<TableColumn<Share, ?>> createColumns() {
    return List.of(
      columnFactory.createQuantityColumn(),
      columnFactory.createSymbolColumn(),
      columnFactory.createPriceColumn(),
      columnFactory.createChangeColumn()
    );
  }
}
