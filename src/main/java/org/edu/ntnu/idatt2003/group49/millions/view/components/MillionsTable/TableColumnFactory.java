package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

public class TableColumnFactory {
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

  public TableColumn<Share, Number> createQuantityColumn() {
    TableColumn<Share, Number> qtyCol = createTableColumn(
      new TableColumn<>("QTY"),
      (share, value) -> {
        System.out.println("Clicked share: " + share);
        System.out.println("Clicked value: " + value);
      },

      (cell, share, quantity) -> {

      }

    );

    qtyCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getQuantity().doubleValue())
    );

    return qtyCol;
  }

  public TableColumn<Share, String> createSymbolColumn() {
    TableColumn<Share, String> symbolCol = createTableColumn(
      new TableColumn<>("Symbol"),
      (share, value) -> {
        System.out.println("Clicked share: " + share);
        System.out.println("Clicked value: " + value);
      },

      (cell, share, value) -> {

      }

    );

    symbolCol.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getStock().getSymbol())
    );

    return symbolCol;
  }

  public TableColumn<Share, Number> createPriceColumn() {
    TableColumn<Share, Number> priceCol = createTableColumn(
      new TableColumn<>("Price"),
      (share, value) -> {
        System.out.println("Clicked share: " + share);
        System.out.println("Clicked value: " + value);
      },

      (cell, share, value) -> {
        cell.setText("$" + value);
      }
    );

    priceCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getStock().getSalesPrice().doubleValue())
    );

    return priceCol;
  }

  public TableColumn<Share, Number> createChangeColumn() {
    TableColumn<Share, Number> changeCol = createTableColumn(
      new TableColumn<>("Change"),
      (share, value) -> {
        System.out.println("Clicked share: " + share);
        System.out.println("Clicked value: " + value);
      },

      (cell, share, value) -> {
        if (share.getQuantity().compareTo(new BigDecimal("150")) > 0) {
          cell.getStyleClass().removeAll("normal-cell", "negative-change");
          cell.getStyleClass().add("positive-change");

          cell.setText("↑" + value + "%");
        } else if (share.getQuantity().compareTo(new BigDecimal("150")) < 0) {
          cell.getStyleClass().removeAll("normal-cell", "positive-change");
          cell.getStyleClass().add("negative-change");

          cell.setText("↓" + value + "%");
        }
      }
    );

    changeCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getQuantity().doubleValue())
    );

    return changeCol;
  }
}
