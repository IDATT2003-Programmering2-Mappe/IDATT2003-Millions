package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.List;
import java.util.Objects;

public abstract class MillionsTable<T> extends MillionsView {
  private final Navigator navigator;
  private final ObservableList<T> list = FXCollections.observableArrayList();
  private final TableView<T> table;
  protected final TableColumnFactory columnFactory;

  public MillionsTable(Navigator navigator) {
    this.columnFactory = new TableColumnFactory();
    this.navigator = navigator;
    this.table = table();

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/table.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox vBox = new VBox();
    vBox.getChildren().add(table);
    return vBox;
  }

  private TableView<T> table() {
    TableView<T> table = new TableView<>();

    table.setRowFactory(tv -> new TableRow<T>() {
      @Override
      protected void updateItem(T share, boolean empty) {
        super.updateItem(share, empty);
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
