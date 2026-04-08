package org.edu.ntnu.idatt2003.group49.millions.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavigationController;

import java.util.Objects;

public class HomeView extends MillionsView {
  public HomeView(NavigationController nav) {
    super(nav);

    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox vbox = new VBox();
    vbox.getChildren().add(new Text("Home View"));
    return vbox;
  }
}
