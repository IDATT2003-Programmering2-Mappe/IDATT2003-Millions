package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

import java.math.BigDecimal;
import java.util.List;

public class TradingTable extends MillionsTable<Share> {
  public TradingTable(Navigator navigator) {
    super(navigator);
  }

  @Override
  protected List<TableColumn<Share, ?>> createColumns() {
    return List.of(
      columnFactory.createSymbolColumn(),
      columnFactory.createPriceColumn(),
      columnFactory.createChangeColumn(),
      columnFactory.createQuantityColumn()
    );
  }
}
