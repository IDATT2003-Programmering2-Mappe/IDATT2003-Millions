package org.edu.ntnu.idatt2003.group49.millions.view.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Header extends StackPane {
  public Header() {
    getStylesheets().add(Objects.requireNonNull(Header.class.getResource("/styles/header.css"), "header.css is null").toExternalForm());
    getChildren().add(createHeader());
  }

  private HBox createHeader() {
    HBox header = new HBox();
    header.getStyleClass().add("header");

    Label title = new Label("Millions");
    title.setId("title");

    header.getChildren().add(title);
    return header;
  }
}
