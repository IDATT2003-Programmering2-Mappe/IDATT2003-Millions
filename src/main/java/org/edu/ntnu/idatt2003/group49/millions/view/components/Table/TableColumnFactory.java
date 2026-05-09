package org.edu.ntnu.idatt2003.group49.millions.view.components.Table;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;

import java.util.function.BiConsumer;

public class TableColumnFactory {

  public TableColumnFactory() {}

  public <V> TableColumn<Share, V> createTableColumn(
    TableColumn<Share, V> column,
    BiConsumer<Share, V> onClick,
    TableCellStyler<Share, V> styler
  ) {
    column.setCellFactory(_ -> {
      TableCell<Share, V> cell = new TableCell<Share, V>() {
        @Override
        protected void updateItem(V item, boolean empty) {
          super.updateItem(item, empty);

          if (empty || item == null) {
            setText(null);
            setGraphic(null);
          } else {
            setText(item.toString());
            setGraphic(null);

            Share share = getTableRow().getItem();
            if (share != null) {
              styler.style(this, share, item);
            }
          }
        }
      };

      cell.setOnMouseClicked(e -> {
        if (!cell.isEmpty()) {
          Share share = cell.getTableRow().getItem();
          V value = cell.getItem();

          if (share != null && value != null) {
            onClick.accept(share, value);
          }
        }
      });

      return cell;
    });

    return column;
  }
}
