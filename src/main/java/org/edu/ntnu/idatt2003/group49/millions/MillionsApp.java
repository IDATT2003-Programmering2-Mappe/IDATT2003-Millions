package org.edu.ntnu.idatt2003.group49.millions;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MillionsApp extends Application {
  private BorderPane root;
  private NavController nav;

  @Override
  public void start(Stage stage) {
    nav.showDashboardView();

    Scene scene = new Scene(root, 1024, 768);
    stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/shrek.png")).toExternalForm()));

    stage.setTitle("Millions");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void init() {
    this.root = new BorderPane();
    this.nav = new NavController(root);
  }

  static void main(String[] args) {
    launch(args);
  }
}
