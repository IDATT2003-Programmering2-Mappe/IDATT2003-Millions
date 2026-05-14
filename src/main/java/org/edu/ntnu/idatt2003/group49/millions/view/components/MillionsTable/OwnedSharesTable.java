package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.scene.control.*;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory.OwnedSharesColumnFactory;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory.TableColumnFactory;

import java.util.List;

public class OwnedSharesTable extends MillionsTable<Share> {
  private final OwnedSharesColumnFactory columnFactory;

  public OwnedSharesTable(Navigator navigator, OwnedSharesColumnFactory columnFactory) {
    this.columnFactory = columnFactory;
    super(navigator);
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
