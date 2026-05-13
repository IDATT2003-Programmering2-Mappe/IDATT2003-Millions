package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;

public class StocksColumnFactory extends TableColumnFactory {
  public StocksColumnFactory() {}

  

  public TableColumn<Stock, String> createSymbolColumn() {
    TableColumn<Stock, String> symbolCol = createTableColumn(
      new TableColumn<>("Symbol"),
      (stock, value) -> {
        System.out.println("Clicked stock: " + stock);
        System.out.println("Clicked value: " + value);
      },

      (cell, stock, value) -> {

      }

    );

    symbolCol.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getSymbol())
    );

    return symbolCol;
  }

  public TableColumn<Stock, Number> createPriceColumn() {
    TableColumn<Stock, Number> priceCol = createTableColumn(
      new TableColumn<>("Price"),
      (stock, value) -> {
        System.out.println("Clicked stock: " + stock);
        System.out.println("Clicked value: " + value);
      },

      (cell, stock, value) -> {
        cell.setText("$" + value);
      }
    );

    priceCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getSalesPrice().doubleValue())
    );

    return priceCol;
  }

  public TableColumn<Stock, Number> createChangeColumn() {
    TableColumn<Stock, Number> changeCol = createTableColumn(
      new TableColumn<>("Change"),
      (stock, value) -> {
        System.out.println("Clicked stock: " + stock);
        System.out.println("Clicked value: " + value);
      },

      (cell, stock, value) -> {
        if (stock.getPriceChangeInPercent().signum() > 0) {
          cell.getStyleClass().removeAll("normal-cell", "negative-change");
          cell.getStyleClass().add("positive-change");

          cell.setText("↑" + value + "%");
        } else if (stock.getPriceChangeInPercent().signum() < 0) {
          cell.getStyleClass().removeAll("normal-cell", "positive-change");
          cell.getStyleClass().add("negative-change");

          cell.setText("↓" + value + "%");
        }
      }
    );

    changeCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getPriceChangeInPercent().doubleValue())
    );

    return changeCol;
  }
}
