package org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsStyler;

import java.math.RoundingMode;

public class SharesColumnFactory extends TableColumnFactory {
  public SharesColumnFactory() {}

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
      new SimpleDoubleProperty(cellData.getValue().getPurchasePrice().doubleValue())
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
        MillionsStyler.updateChangeStyle(value, cell);
      }
    );

    changeCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getChangeSincePurchase().setScale(2, RoundingMode.HALF_UP).doubleValue())
    );

    return changeCol;
  }
}
