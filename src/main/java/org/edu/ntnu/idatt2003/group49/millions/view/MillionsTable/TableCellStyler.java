package org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable;

import javafx.scene.control.TableCell;

@FunctionalInterface
public interface TableCellStyler<S, V> {
  void style(TableCell<S, V> cell, S rowItem, V cellValue);
}
