package org.edu.ntnu.idatt2003.group49.millions.view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavigationController;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class Header extends MillionsView {
  public Header(NavigationController nav) {
    super(nav);

    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    HBox body = new HBox();
    body.getStyleClass().add("header");

    body.getChildren().addAll(
      title(),
      navBar()
    );
    return body;
  }

  private Label title() {
    Label title = new Label("Millions");
    title.getStyleClass().add("title");
    return title;
  }

  private HBox navBar() {
    HBox nav = new HBox();
    nav.getStyleClass().add("navbar");

    // Buttons
    Button btnStocks = new Button("Stocks");
    btnStocks.setOnAction(e -> this.nav.showHomeView());

    nav.getChildren().addAll(
        btnStocks
    );
    return nav;
  }
}
