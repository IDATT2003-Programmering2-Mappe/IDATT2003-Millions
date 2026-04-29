package org.edu.ntnu.idatt2003.group49.millions;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.controller.ViewFactory;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.MillionsFileReader;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.MillionsFileWriter;

import java.nio.file.Path;
import java.util.Objects;

public class MillionsApp extends Application {
  private BorderPane root;
  private NavController nav;
  private Exchange exchange;
  private ViewFactory viewFactory;

  @Override
  public void start(Stage stage) {
    nav.goToDashboard();

    Scene scene = new Scene(root, 1024, 768);
    stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/shrek.png")).toExternalForm()));

    stage.setTitle("Millions");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void init() {
    this.exchange = new Exchange("Nasdaq", MillionsFileReader.convertCSVFileToStocksList(Path.of("src/main/resources/sp500.csv")));
    this.root = new BorderPane();
    this.nav = new NavController(root);
    this.viewFactory = new ViewFactory(nav, new ExchangeController(exchange));
    this.nav.setViewFactory(viewFactory);

  }

  static void main(String[] args) {
    launch(args);
  }
}
