package org.edu.ntnu.idatt2003.group49.millions;

import javafx.scene.layout.BorderPane;
import org.edu.ntnu.idatt2003.group49.millions.model.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.MillionsService;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MillionsApp extends Application {
  private List<Exchange> exchanges;
  private Logger logger = Logger.getLogger(MillionsApp.class.getName());

  @Override
  public void init() {
    exchanges = new ArrayList<>();
    List<Stock> stocks = new ArrayList<>();
    final Stock nvidiaStock = new Stock("NVDA", "Nvidia", new BigDecimal("191.27"));
    final Stock appleStock = new Stock("AAPL", "Apple", new BigDecimal("276.43"));
    final Stock microsoftStock = new Stock("MSFT", "Microsoft", new BigDecimal("404.68"));
    final Stock amazonStock = new Stock("AMZN", "Amazon", new BigDecimal("204.62"));
    final Stock googleStockA = new Stock("GOOGL", "Alphabet Inc. (Class A)", new BigDecimal("311.20"));
    final Stock googleStockB = new Stock("GOOG", "Alphabet Inc. (Class C)", new BigDecimal("311.62"));

    stocks.add(nvidiaStock);
    stocks.add(appleStock);
    stocks.add(microsoftStock);
    stocks.add(amazonStock);
    stocks.add(googleStockA);
    stocks.add(googleStockB);

    stocks.forEach(System.out::println);

    Exchange nasdaq = new Exchange("NASDAQ", stocks);
    this.exchanges.add(nasdaq);
  }

  @Override
  public void start(Stage stage) {
    MillionsService millionsService = new MillionsService();

    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1024, 768);
    System.out.println(this.exchanges);

    stage.setTitle("Millions");
    stage.setScene(scene);
    // stage.show();
  }

  static void main(String[] args) {
    launch(args);
  }
}
