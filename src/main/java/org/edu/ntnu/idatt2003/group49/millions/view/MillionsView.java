package org.edu.ntnu.idatt2003.group49.millions.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavigationController;

public abstract class MillionsView extends StackPane {
  protected final NavigationController nav;

  protected MillionsView(NavigationController nav) {
    this.nav = nav;
  }

  protected abstract Pane build();
}
