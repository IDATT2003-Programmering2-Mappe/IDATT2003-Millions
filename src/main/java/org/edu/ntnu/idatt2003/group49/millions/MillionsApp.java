package org.edu.ntnu.idatt2003.group49.millions;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.edu.ntnu.idatt2003.group49.millions.helper.ViewFactory;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.CSVReader;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.CSVWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class MillionsApp extends Application {
  private BorderPane root;
  private NavController nav;

  @Override
  public void start(Stage stage) {
    nav.goToLandingPage();

    Scene scene = new Scene(root, 1024, 768);
    stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/shrek.png")).toExternalForm()));

    stage.setTitle("Millions");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void init() throws IOException {
    Exchange exchange = new Exchange("NASDAQ", CSVReader.convertCSVToStocksList(Path.of("src/main/resources/sp500.csv")));
    CSVWriter.writeStockDataToFile(Path.of("data/stock_data.csv"), exchange.getStockMap());
    this.root = new BorderPane();
    this.nav = new NavController(root);

    ViewFactory viewFactory = new ViewFactory(nav, exchange);
    this.nav.setViewFactory(viewFactory);
  }

  static void main(String[] args) {
    launch(args);
  }
}
