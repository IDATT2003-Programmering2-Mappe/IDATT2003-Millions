package org.edu.ntnu.idatt2003.group49.millions.view.components.Table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class StocksTable extends MillionsView {
  private final Navigator navigator;

  public StocksTable(Navigator navigator) {
    this.navigator = navigator;

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/table.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox vBox = new VBox();
    vBox.getChildren().add(table());
    return vBox;
  }

  private TableView<Share> table() {
    TableColumnFactory columnFactory = new TableColumnFactory();

    TableColumn<Share, Number> qtyCol = columnFactory.createTableColumn(

      new TableColumn<>("Quantity"),
      (share, value) -> {
        System.out.println("Clicked share: " + share);
        System.out.println("Clicked value: " + value);
      },

      (cell, share, quantity) -> {
        if (share.getQuantity().compareTo(new BigDecimal("200")) < 0) {
          cell.setStyle("-fx-background-color: #dcfce7");
        }
      }

    );

    qtyCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getQuantity().doubleValue())
    );

    TableColumn<Share, String> nameCol = columnFactory.createTableColumn(

      new TableColumn<>("Name"),
      (share, value) -> {
        System.out.println("Clicked share: " + share);
        System.out.println("Clicked value: " + value);
      },

      (cell, share, value) -> {

      }

    );

    nameCol.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getStock().getSymbol())
    );

    TableColumn<Share, Number> priceCol = columnFactory.createTableColumn(

      new TableColumn<>("Price"),
      (share, value) -> {
        System.out.println("Clicked share: " + share);
        System.out.println("Clicked value: " + value);
      },

      (cell, share, value) -> {

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
      }

    );

    changeCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getQuantity().doubleValue())
    );

    TableView<Share> table = new TableView<>();

    table.setRowFactory(tv -> new TableRow<Share>() {
      @Override
      protected void updateItem(Share share, boolean empty) {
        super.updateItem(share, empty);

//        if (empty || share == null) {
//          setStyle("");
//        } else if (share.getQuantity().compareTo(new BigDecimal("100")) > 0) {
//          setStyle("-fx-background-color: #dcfce7;");
//        } else {
//          setStyle("");
//        }
      }
    });


    table.getColumns().setAll(List.of(qtyCol, nameCol, priceCol, changeCol));


    ObservableList<Share> list = FXCollections.observableArrayList();
    list.setAll(
      new Share(
        new Stock("NVDA", "Nvidia", new BigDecimal("100")), new BigDecimal("200"), new BigDecimal("150")
      ),
      new Share(
        new Stock("APPL", "Apple", new BigDecimal("400")), new BigDecimal("123"), new BigDecimal("100")
      )
    );

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    table.getColumns().forEach(col -> {
      col.setReorderable(false);
    });

    table.setItems(list);

    return table;
  }
}
