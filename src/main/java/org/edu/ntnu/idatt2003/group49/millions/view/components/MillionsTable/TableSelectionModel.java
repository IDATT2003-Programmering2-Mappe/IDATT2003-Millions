package org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsTable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TableSelectionModel<T> {
  private final ObjectProperty<T> selectedItem = new SimpleObjectProperty<>();

  public ObjectProperty<T> selectedItemProperty() {
    return selectedItem;
  }

  public T getSelectedItem() {
    return selectedItem.get();
  }

  public void setSelectedItem(T item) {
    selectedItem.set(item);
  }
}
