package org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Transaction;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory.TransactionsColumnFactory;

import java.util.List;

public class TransactionTable extends MillionsTable<Transaction> {
  private final TransactionsColumnFactory columnFactory;

  public TransactionTable(TransactionsColumnFactory columnFactory, TableSelectionModel<Transaction> selectionModel) {
    this.columnFactory = columnFactory;
    super();

    table.setRowFactory(_ -> {
      TableRow<Transaction> row = new TableRow<>();

      row.setOnMouseClicked(e -> {
        if (!row.isEmpty()) {
          selectionModel.setSelectedItem(row.getItem());
        }
      });

      return row;
    });
  }

  @Override
  protected List<TableColumn<Transaction, ?>> createColumns() {
    return List.of(
        columnFactory.createWeekColumn(),
        columnFactory.createTypeColumn(),
        columnFactory.createSymbolColumn(),
        columnFactory.createQTYColumn(),
        columnFactory.createPriceColumn(),
        columnFactory.createTotalPriceColumn()
    );
  }
}
