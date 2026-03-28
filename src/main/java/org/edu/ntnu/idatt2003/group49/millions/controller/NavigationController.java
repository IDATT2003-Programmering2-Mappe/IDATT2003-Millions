package org.edu.ntnu.idatt2003.group49.millions.controller;

import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.view.HomeView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.Header;

public class NavigationController {
  private final BorderPane root;

  public NavigationController(BorderPane root) {
    this.root = root;
  }

  public void showHomeView() {
    root.setTop(new Header(this));
    root.setCenter(new HomeView(this));
  }
}
