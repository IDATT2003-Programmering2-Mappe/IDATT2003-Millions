package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.TableCellStyler;

import java.util.function.BiConsumer;

public abstract class TableColumnFactory {
  public TableColumnFactory() {}

  public <T, V> TableColumn<T, V> createTableColumn(
    TableColumn<T, V> column,
    BiConsumer<T, V> onClick,
    TableCellStyler<T, V> styler
  ) {
    column.setCellFactory(_ -> {
      TableCell<T, V> cell = new TableCell<T, V>() {
        @Override
        protected void updateItem(V item, boolean empty) {
          super.updateItem(item, empty);

          getStyleClass().add("default-cell");

          if (empty || item == null) {
            setText(null);
            setGraphic(null);
          } else {
            setText(item.toString());
            setGraphic(null);

            T rowItem = getTableRow().getItem();
            if (rowItem != null) {
              styler.style(this, rowItem, item);
            }
          }
        }
      };

      cell.setOnMouseClicked(e -> {
        if (!cell.isEmpty()) {
          T rowItem = cell.getTableRow().getItem();
          V value = cell.getItem();

          if (rowItem != null && value != null) {
            onClick.accept(rowItem, value);
          }
        }
      });

      return cell;
    });

    return column;
  }
}
