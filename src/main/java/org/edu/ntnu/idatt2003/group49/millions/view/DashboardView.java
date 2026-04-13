package org.edu.ntnu.idatt2003.group49.millions.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;

import java.util.Objects;

public class DashboardView extends MillionsView {
  public DashboardView(NavController navController) {
    super(navController);
    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
    getChildren().add(build());
  }

  @Override
  public Pane build() {
    VBox body = new VBox();

    body.getChildren().addAll(
      new Text("hello")
    );

    return body;
  }
}
