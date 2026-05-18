package org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Transaction;

import java.math.BigDecimal;

public class TransactionsColumnFactory extends TableColumnFactory {
  public TransactionsColumnFactory() {
  }

  public TableColumn<Transaction, Number> createWeekColumn() {
    TableColumn<Transaction, Number> weekCol = createTableColumn(
        new TableColumn<>("Week"),
        (transaction, value) -> {
          System.out.println("Clicked transaction: " + transaction);
          System.out.println("Clicked value: " + value);
        },
        (cell, transaction, value) -> {
        }
    );

    weekCol.setCellValueFactory(cellData ->
        new SimpleIntegerProperty(cellData.getValue().getWeek())
    );

    return weekCol;
  }

  public TableColumn<Transaction, String> createTypeColumn() {
    TableColumn<Transaction, String> typeCol = createTableColumn(
        new TableColumn<>("Type"),
        (transaction, value) -> {
          System.out.println("Clicked transaction: " + transaction);
          System.out.println("Clicked value: " + value);
        },
        (cell, transaction, value) -> {
        }
    );

    typeCol.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getTransactionType())
    );

    return typeCol;
  }

  public TableColumn<Transaction, String> createSymbolColumn() {
    TableColumn<Transaction, String> symbolCol = createTableColumn(
        new TableColumn<>("Symbol"),
        (transaction, value) -> {
          System.out.println("Clicked transaction: " + transaction);
          System.out.println("Clicked value: " + value);
        },
        (cell, transaction, value) -> {
        }
    );

    symbolCol.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getShare().getStock().getSymbol())
    );

    return symbolCol;
  }

  public TableColumn<Transaction, BigDecimal> createQTYColumn() {
    TableColumn<Transaction, BigDecimal> qtyCol = createTableColumn(
        new TableColumn<>("QTY"),
        (transaction, value) -> {
          System.out.println("Clicked transaction: " + transaction);
          System.out.println("Clicked value: " + value);
        },
        (cell, transaction, value) -> {
        }
    );

    qtyCol.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getShare().getQuantity())
    );

    return qtyCol;
  }

  public TableColumn<Transaction, BigDecimal> createPriceColumn() {
    TableColumn<Transaction, BigDecimal> priceCol = createTableColumn(
        new TableColumn<>("Price"),
        (transaction, value) -> {
          System.out.println("Clicked transaction: " + transaction);
          System.out.println("Clicked value: " + value);
        },
        (cell, transaction, value) -> {
          cell.setText("$" + value);
        }
    );

    priceCol.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getShare().getPurchasePrice())
    );

    return priceCol;
  }

  public TableColumn<Transaction, BigDecimal> createTotalPriceColumn() {
    TableColumn<Transaction, BigDecimal> totalPriceCol = createTableColumn(
        new TableColumn<>("Total"),
        (transaction, value) -> {
          System.out.println("Clicked transaction: " + transaction);
          System.out.println("Clicked value: " + value);
        },
        (cell, transaction, value) -> {
          cell.setText("$" + value);
        }
    );

    totalPriceCol.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getCalculator().calculateTotal())
    );

    return totalPriceCol;
  }

}
