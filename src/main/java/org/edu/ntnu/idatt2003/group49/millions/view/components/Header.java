package org.edu.ntnu.idatt2003.group49.millions.view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class Header extends MillionsView {
  public Header(NavController navController) {
    super(navController);
    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    HBox body = new HBox();
    body.getStyleClass().add("header");

    body.getChildren().addAll(
      title(),
      navBar(),
      userAvatar()
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
    btnStocks.setOnAction(e -> this.navController.showDashboardView());

    nav.getChildren().addAll(
        btnStocks
    );
    return nav;
  }

  private StackPane userAvatar() {
    StackPane userAvatar = new StackPane();
    userAvatar.getStyleClass().add("userAvatar");
    ImageView avatar = new ImageView(
      new Image(Objects.requireNonNull(getClass().getResource("/images/shrek.png")).toExternalForm())
    );
    avatar.setFitHeight(64);
    avatar.setFitWidth(64);
    userAvatar.getChildren().addAll(avatar);
    return userAvatar;
  }
}
