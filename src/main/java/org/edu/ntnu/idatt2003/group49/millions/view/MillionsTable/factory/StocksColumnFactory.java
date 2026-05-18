package org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.player.Portfolio;
import org.edu.ntnu.idatt2003.group49.millions.view.popups.BuySharePopup;

import java.util.logging.Logger;

public class StocksColumnFactory extends TableColumnFactory {
  static Logger logger = Logger.getLogger(StocksColumnFactory.class.getName());
  private final ExchangeController exchangeController;
  private BuySharePopup buyStockPopup;

  public StocksColumnFactory(ExchangeController exchangeController, BuySharePopup buyStockPopup) {
    this.exchangeController = exchangeController;
    this.buyStockPopup = buyStockPopup;
  }

  public TableColumn<Stock, Number> createIndexColumn(ObservableList<Stock> originalItems) {
    TableColumn<Stock, Number> indexCol = createTableColumn(
      new TableColumn<>("#"),
      (stock, value) -> {
        System.out.println("Clicked stock: " + stock);
        System.out.println("Clicked value: " + value);
      },

      (cell, stock, value) -> {
        cell.setText(value + ".");
      }

    );

    indexCol.setCellValueFactory(cellData -> {
      Stock stock = cellData.getValue();
      int index = originalItems.indexOf(stock) + 1;
      return new SimpleIntegerProperty(index);
    });

    indexCol.setPrefWidth(50);
    return indexCol;
  }

  public TableColumn<Stock, String> createCompanyColumn() {
    TableColumn<Stock, String> companyCol = createTableColumn(
      new TableColumn<>("Company"),
      (stock, value) -> {
        System.out.println("Clicked stock: " + stock);
        System.out.println("Clicked value: " + value);
      },

      (cell, stock, value) -> {

      }

    );

    companyCol.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getCompany())
    );

    companyCol.setMinWidth(200);
    return companyCol;
  }

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
        if (stock.getCurrentChange().signum() > 0) {
          cell.getStyleClass().removeAll("normal-cell", "negative-change", "zero-change");
          cell.getStyleClass().add("positive-change");

          cell.setText("+" + value + "%");
        } else if (stock.getCurrentChange().signum() < 0) {
          cell.getStyleClass().removeAll("normal-cell", "positive-change", "zero-change");
          cell.getStyleClass().add("negative-change");

          cell.setText(value + "%");
        } else {
          cell.getStyleClass().removeAll("normal-cell", "positive-change", "negative-change");
          cell.getStyleClass().add("zero-change");

          cell.setText(value + "%");
        }
      }
    );

    changeCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getCurrentChange().doubleValue())
    );

    return changeCol;
  }

  public TableColumn<Stock, String> createBuyColumn() {
    TableColumn<Stock, String> buyColumn = createTableColumn(
      new TableColumn<>(""),
      (stock, value) -> {
        buyStockPopup.show(stock, request -> {
          logger.info("Trying to purchase " + stock.getCompany() + " shares");
          try {
            exchangeController.buy(request);
            logger.info("Player [" + request.player().getName() + "] successfully purchased [" + request.quantity() + "] shares of [" + stock.getCompany() + "] stock");
          } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
            logger.severe("Could not continue with purchase! " + e.getMessage());
          }

          request.player().getTransactionArchive().getPurchases(exchangeController.getWeek()).forEach(System.out::println);
          System.out.println(request.player().getMoney());
        });

        System.out.println("Clicked stock: " + stock);
        System.out.println("Clicked value: " + value);
      },

      (cell, stock, value) -> {
        cell.getStyleClass().add("buy-cell");
      }
    );

    buyColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty("BUY")
    );

    return buyColumn;
  }

  public TableColumn<Stock, String> createSellColumn() {
    TableColumn<Stock, String> sellColumn = createTableColumn(
      new TableColumn<>(""),
      (stock, value) -> {
        System.out.println("Clicked stock: " + stock);
        System.out.println("Clicked value: " + value);
      },

      (cell, stock, value) -> {
        cell.getStyleClass().remove("");
        cell.getStyleClass().add("sell-cell");
      }
    );

    sellColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty("SELL")
    );

    return sellColumn;
  }
}
