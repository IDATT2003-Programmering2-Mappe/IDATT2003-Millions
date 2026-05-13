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
    TableColumn<Share, Number> qtyCol = columnFactory.createTableColumn(
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

    TableColumn<Share, String> symbolCol = columnFactory.createTableColumn(
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

    TableColumn<Share, Number> priceCol = columnFactory.createTableColumn(
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

    TableColumn<Share, Number> changeCol = columnFactory.createTableColumn(
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

    return List.of(qtyCol, symbolCol, priceCol, changeCol);
  }
}
