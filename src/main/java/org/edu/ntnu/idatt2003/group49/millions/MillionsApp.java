package org.edu.ntnu.idatt2003.group49.millions;

import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.controller.NavigationController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MillionsApp extends Application {
  private BorderPane root;
  private NavigationController nav;

  @Override
  public void start(Stage stage) {
    nav.showHomeView();

    Scene scene = new Scene(root, 1024, 768);
    stage.setTitle("Millions");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void init() {
    this.root = new BorderPane();
    this.nav = new NavigationController(root);
//    List<Stock> stocks;
//    final Stock nvidiaStock = new Stock("NVDA", "Nvidia", new BigDecimal("191.27"));
//    final Stock appleStock = new Stock("AAPL", "Apple", new BigDecimal("276.43"));
//    final Stock microsoftStock = new Stock("MSFT", "Microsoft", new BigDecimal("404.68"));
//    final Stock amazonStock = new Stock("AMZN", "Amazon", new BigDecimal("204.62"));
//    final Stock googleStockA = new Stock("GOOGL", "Alphabet Inc. (Class A)", new BigDecimal("311.20"));
//    final Stock googleStockB = new Stock("GOOG", "Alphabet Inc. (Class C)", new BigDecimal("311.62"));
//
//    stocks = new ArrayList<>();
//    stocks.add(nvidiaStock);
//    stocks.add(appleStock);
//    stocks.add(microsoftStock);
//    stocks.add(amazonStock);
//    stocks.add(googleStockA);
//    stocks.add(googleStockB);
//
//    Random rand = new Random();
  }

  static void main(String[] args) {
    launch(args);
  }
}
