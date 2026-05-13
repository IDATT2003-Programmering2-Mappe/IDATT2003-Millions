package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.scene.control.*;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;

import java.util.List;

public class OwnedSharesTable extends MillionsTable<Share> {
  public OwnedSharesTable(Navigator navigator) {
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
