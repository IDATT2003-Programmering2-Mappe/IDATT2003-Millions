package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.components.MillionsChart.MillionsChart;

import java.math.BigDecimal;
import java.util.Objects;

public class StockInfo extends MillionsView {
  private Stock stock;
  private final MillionsChart chart;

  private Label symbolLabel;
  private Label companyLabel;
  private Label priceLabel;
  private Label changeLabel;
  private Label highestLabel;
  private Label lowestLabel;

  public StockInfo() {
    this.chart = new MillionsChart();
    this.stock = new Stock("Milli", "Millions", new BigDecimal("6767"));

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/tradingpage.css")
    ).toExternalForm());
   getChildren().add(build());
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }

  @Override
  protected Pane build() {
    VBox body = new VBox();
    body.getChildren().addAll(
      title(),
      chart,
      createInfoSection()
    );

    chart.addData(0, 500);
    chart.addData(1, 100);

    return body;
  }

  private HBox title() {
    this.symbolLabel = new Label(stock.getSymbol());
    symbolLabel.getStyleClass().add("symbol");

    HBox title = new HBox();
    title.getStyleClass().add("title");
    title.getChildren().add(symbolLabel);

    return title;
  }

  private VBox createInfoSection() {
    this.companyLabel = new Label(stock.getCompany());
    this.priceLabel = new Label(stock.getSalesPrice().toString());
    this.changeLabel = new Label(stock.getCurrentChange().toString());
    this.highestLabel = new Label(stock.getHighestPrice().toString());
    this.lowestLabel = new Label(stock.getLowestPrice().toString());

    VBox infoSection = new VBox();
    infoSection.getChildren().addAll(
      createInfoBox(new Label("Company:"), companyLabel),
      createInfoBox(new Label("Current price:"), priceLabel),
      createInfoBox(new Label("Change:"), changeLabel),
      createInfoBox(new Label("Highest price:"), highestLabel),
      createInfoBox(new Label("Lowest price:"), lowestLabel)
    );

    return infoSection;
  }

  private HBox createInfoBox(Label identifier, Label info) {
    identifier.getStyleClass().add("identifier");
    info.getStyleClass().add("info");

    HBox infoBox = new HBox();
    infoBox.getStyleClass().add("info-box");
    infoBox.getChildren().addAll(
      identifier,
      info
    );

    return infoBox;
  }

  public void updateStockInfo() {
    symbolLabel.setText(stock.getSymbol());
    companyLabel.setText(stock.getCompany());
    priceLabel.setText(stock.getSalesPrice().toString());
    changeLabel.setText(stock.getCurrentChange().toString());
    highestLabel.setText(stock.getHighestPrice().toString());
    lowestLabel.setText(stock.getLowestPrice().toString());

    chart.clearData();
    int week = 0;
    for (BigDecimal price : stock.getPrices()) {
      chart.addData(week, price);
      week++;
    }
  }
}
