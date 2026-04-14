package org.edu.ntnu.idatt2003.group49.millions.view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class Header extends MillionsView {
  private final NavController navController;

  public Header(NavController navController) {
    this.navController = navController;
    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/header.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox body = new VBox();
    BorderPane topSection = new BorderPane();
    topSection.getStyleClass().add("header");

    topSection.setLeft(title());
    topSection.setCenter(searchBar());
    topSection.setRight(userSection());

    body.getChildren().addAll(
      topSection,
      navBar()
    );
    return body;
  }

  private VBox title() {
    VBox titleContainer = new VBox();
    titleContainer.getStyleClass().add("title-container");

    Label title = new Label("Millions");
    title.getStyleClass().add("title");

    Rectangle rect = new Rectangle();
    rect.getStyleClass().add("title-rect");
    rect.widthProperty().bind(title.widthProperty());
    rect.setHeight(5);

    titleContainer.getChildren().addAll(
      title,
      rect
    );
    return titleContainer;
  }

  private HBox searchBar() {
    HBox searchBarContainer = new HBox();
    searchBarContainer.getStyleClass().add("searchbar-container");

    HBox searchBar = new HBox();
    searchBar.getStyleClass().add("searchbar");

    TextField searchField = new TextField();
    searchField.getStyleClass().add("search-field");
    searchField.setPromptText("Search");

    searchBar.getChildren().addAll(
      searchField
    );
    searchBarContainer.getChildren().add(searchBar);
    return searchBarContainer;
  }

  private HBox navBar() {
    HBox nav = new HBox();
    nav.getStyleClass().add("navbar");

    // Buttons
    Button btnDashboard = new Button("Dashboard");
    btnDashboard.getStyleClass().add("navbar-btn");

    Button btnStocks = new Button("Stocks");
    btnStocks.getStyleClass().add("navbar-btn");
    btnStocks.setOnAction(e -> this.navController.showDashboardView());

    nav.getChildren().addAll(
      btnDashboard,
      btnStocks
    );
    return nav;
  }

  private HBox userSection() {
    HBox userSection = new HBox();
    userSection.getStyleClass().add("user-section");

    VBox stats = new VBox();
    stats.getStyleClass().add("header-stats");

    VBox info = new VBox();
    info.getStyleClass().add("header-info");

    Label netWorth = new Label("Cash:");
    netWorth.getStyleClass().add("header-label");

    Label money = new Label("100 000 000$");
    money.getStyleClass().add("header-label");

    Circle avatar = new Circle(32);
    avatar.getStyleClass().add("header-avatar");
    Image img = new Image(Objects.requireNonNull(getClass().getResource("/images/shrek.png")).toExternalForm());
    avatar.setFill(new ImagePattern(img));

    stats.getChildren().addAll(
      netWorth,
      money
    );

    info.getChildren().addAll(
      avatar
    );

    userSection.getChildren().addAll(
      stats,
      info
    );
    return userSection;
  }
}
