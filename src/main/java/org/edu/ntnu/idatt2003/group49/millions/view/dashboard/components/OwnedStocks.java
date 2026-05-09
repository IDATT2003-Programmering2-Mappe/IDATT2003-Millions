package org.edu.ntnu.idatt2003.group49.millions.view.dashboard.components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OwnedStocks extends MillionsView {
  private final NavController navController;

  public OwnedStocks(NavController navController) {
    this.navController = navController;

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
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


    TableColumn<Share, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getStock().getSymbol()));

    TableColumn<Share, Number> priceCol = new TableColumn<>("Price");
    priceCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getStock().getSalesPrice().doubleValue()));

    TableColumn<Share, Number> changeCol = new TableColumn<>("Change");
    changeCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getQuantity().doubleValue()));

    TableView<Share> table = new TableView<>();
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    table.getColumns().setAll(List.of(getQuantityColumn(), nameCol, priceCol, changeCol));

    table.setItems(FXCollections.observableArrayList(new Share(new Stock("NVDA", "Nvidia", new BigDecimal("100")), new BigDecimal("200"), new BigDecimal("150"))));
    return table;
  }

  private TableColumn<Share, Number> getQuantityColumn() {
    TableColumn<Share, Number> qtyCol = new TableColumn<>("QTY");

    qtyCol.setCellValueFactory(cellData ->
      new SimpleDoubleProperty(cellData.getValue().getQuantity().doubleValue())
    );

    qtyCol.setCellFactory(column -> {
      TableCell<Share, Number> cell = new TableCell<Share, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);

          if (empty) {
            setGraphic(null);
          } else {
            setText(item.toString());
          }
        }
      };

      cell.setOnMouseClicked(e -> {
        if (!cell.isEmpty()) {
          Share share = cell.getTableRow().getItem();
          System.out.println("Clicked share: " + share);
          System.out.println("Clicked cell: " + share.getQuantity());
        }
      });
      return cell;
    });

    return qtyCol;
  }
}
