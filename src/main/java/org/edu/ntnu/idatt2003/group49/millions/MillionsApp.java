package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.controller.MainController;
import org.edu.ntnu.idatt2003.group49.millions.model.Exchange;
import org.edu.ntnu.idatt2003.group49.millions.model.MillionsService;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.MillionsFileReader;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.MillionsFileWriter;

public class MillionsApp extends Application {
  Logger logger = Logger.getLogger(MillionsApp.class.getName());

  @Override
  public void start(Stage stage) {
    MillionsService millionsService = new MillionsService();
    MainController controller = new MainController(millionsService);

    Scene root = new Scene(controller.getMainView(), 1024, 768);

    stage.setTitle("Millions");
    stage.setScene(root);
    // stage.show();
  }

  @Override
  public void init() {
    List<Stock> stocks;
    final Stock nvidiaStock = new Stock("NVDA", "Nvidia", new BigDecimal("191.27"));
    final Stock appleStock = new Stock("AAPL", "Apple", new BigDecimal("276.43"));
    final Stock microsoftStock = new Stock("MSFT", "Microsoft", new BigDecimal("404.68"));
    final Stock amazonStock = new Stock("AMZN", "Amazon", new BigDecimal("204.62"));
    final Stock googleStockA = new Stock("GOOGL", "Alphabet Inc. (Class A)", new BigDecimal("311.20"));
    final Stock googleStockB = new Stock("GOOG", "Alphabet Inc. (Class C)", new BigDecimal("311.62"));

    stocks = new ArrayList<>();
    stocks.add(nvidiaStock);
    stocks.add(appleStock);
    stocks.add(microsoftStock);
    stocks.add(amazonStock);
    stocks.add(googleStockA);
    stocks.add(googleStockB);

    Random rand = new Random();

    Exchange nasdaq = new Exchange("Nasdaq", stocks);
    Map<String, Stock> stockMap = nasdaq.getStockMap();
    for(Map.Entry<String, Stock> entry : stockMap.entrySet()) {
      System.out.println(entry.getValue().getSalesPrice());
    }
    nasdaq.advance();
    Map<String, Stock> afstockMap = nasdaq.getStockMap();
    for(Map.Entry<String, Stock> entry : afstockMap.entrySet()) {
      System.out.println(entry.getValue().getSalesPrice());
    }

    List<Stock> sp500stocks = new ArrayList<>();
    try {
      sp500stocks.addAll(MillionsFileReader.convertCSVFileToStocks(Path.of("src/main/resources/sp500.csv")));
    } catch(IOException e) {
      logger.severe(e.getMessage());
      sp500stocks.add(new Stock("ERR", "Error", new BigDecimal("0.0")));
    }
    sp500stocks.forEach(System.out::println);

    try {
      MillionsFileWriter.writeStockDataToFile(Path.of("src/main/resources/test.csv"), stockMap);
    } catch (IOException e) {
      logger.severe(e.getMessage());
    }
  }

  static void main(String[] args) {
    launch(args);
  }
}
