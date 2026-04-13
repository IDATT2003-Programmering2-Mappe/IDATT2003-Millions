package org.edu.ntnu.idatt2003.group49.millions.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;

public abstract class MillionsView extends StackPane {
  protected final NavController navController;

  protected MillionsView(NavController navController) {
    this.navController = navController;
  }

  protected abstract Pane build();
}
