package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable.factory.TableColumnFactory;

import java.util.List;
import java.util.Objects;

public abstract class MillionsTable<T> extends MillionsView {
  private final Navigator navigator;
  private final ObservableList<T> list = FXCollections.observableArrayList();
  private final TableView<T> table;

  public MillionsTable(Navigator navigator) {
    this.navigator = navigator;
    this.table = table();

    setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/table.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  public ObservableList<T> getList() {
    return list;
  }

  public TableView<T> getTable() {
    return table;
  }

  protected Pane build() {
    VBox vBox = new VBox(table);

    vBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    VBox.setVgrow(table, Priority.ALWAYS);
    table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    return vBox;
  }

  private TableView<T> table() {
    TableView<T> table = new TableView<>();

    table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    table.setMinHeight(0);

    table.setRowFactory(tv -> new TableRow<T>() {
      @Override
      protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
      }
    });

    table.getColumns().setAll(createColumns());

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    table.getColumns().forEach(col -> {
      col.setReorderable(false);
    });

    return table;
  }

  protected abstract List<TableColumn<T, ?>> createColumns();

  public void setItems(List<T> items) {
    list.setAll(items);
    table.setItems(list);
  }
}
