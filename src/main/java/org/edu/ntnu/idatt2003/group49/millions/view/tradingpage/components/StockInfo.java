package org.edu.ntnu.idatt2003.group49.millions.view.tradingpage.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
    VBox stockInfo = new VBox();
    stockInfo.getStyleClass().add("stock-info");
    stockInfo.getChildren().addAll(
      new VBox(title(), chart),
      createInfoSection()
    );

    chart.addData(0, 500);
    chart.addData(1, 100);

    return stockInfo;
  }

  private HBox title() {
    this.symbolLabel = new Label(stock.getSymbol());
    symbolLabel.getStyleClass().add("symbol");

    HBox title = new HBox();
    title.getStyleClass().add("title");
    title.getChildren().add(symbolLabel);

    return title;
  }

  private HBox createInfoSection() {
    this.companyLabel = new Label(stock.getCompany());
    this.priceLabel = new Label(stock.getSalesPrice().toString());
    this.changeLabel = new Label(stock.getCurrentChange().toString());
    this.highestLabel = new Label(stock.getHighestPrice().toString());
    this.lowestLabel = new Label(stock.getLowestPrice().toString());

    companyLabel.getStyleClass().addAll("info-label", "company-label");
    priceLabel.getStyleClass().addAll("info-label", "price-label");
    changeLabel.getStyleClass().addAll("info-label", "change-label");
    highestLabel.getStyleClass().addAll("info-label", "highest-label");
    lowestLabel.getStyleClass().addAll("info-label", "lowest-label");

    HBox infoSection = new HBox();

    infoSection.getStyleClass().add("info-section");

    infoSection.getChildren().addAll(
      createIdentifierBox(),
      createInfoBox()
    );

    return infoSection;
  }

  private VBox createIdentifierBox() {
    Label companyIDLabel = new Label("Company:");
    Label priceIDLabel = new Label("Current price:");
    Label changeIDLabel = new Label("Change:");
    Label highestIDLabel = new Label("Highest price:");
    Label lowestIDLabel = new Label("Lowest price:");

    companyIDLabel.getStyleClass().addAll("identifier-label");
    priceIDLabel.getStyleClass().addAll("identifier-label");
    changeIDLabel.getStyleClass().addAll("identifier-label");
    highestIDLabel.getStyleClass().addAll("identifier-label");
    lowestIDLabel.getStyleClass().addAll("identifier-label");

    VBox identifierBox = new VBox();

    identifierBox.getStyleClass().add("identifier-box");

    identifierBox.getChildren().addAll(
      companyIDLabel,
      priceIDLabel,
      changeIDLabel,
      highestIDLabel,
      lowestIDLabel
    );

    return identifierBox;
  }

  private VBox createInfoBox() {
    VBox infoBox = new VBox();

    infoBox.getStyleClass().add("info-box");

    infoBox.getChildren().addAll(
      companyLabel,
      priceLabel,
      changeLabel,
      highestLabel,
      lowestLabel
    );

    return infoBox;
  }

  private void updateChangeStyle(BigDecimal change) {
    changeLabel.getStyleClass().removeAll("positive-change", "negative-change", "zero-change");

    if (change.signum() > 0) {
      changeLabel.setText("+" + change + "%");
      changeLabel.getStyleClass().add("positive-change");
    }
    else if (change.signum() < 0) {
      changeLabel.setText(change + "%");
      changeLabel.getStyleClass().add("negative-change");
    }
    else {
      changeLabel.setText(change + "%");
      changeLabel.getStyleClass().add("zero-change");
    }
  }

  public void updateStockInfo() {
    symbolLabel.setText(stock.getSymbol());
    companyLabel.setText(stock.getCompany());
    priceLabel.setText(stock.getSalesPrice().toString());
    changeLabel.setText(stock.getCurrentChange().toString());
    highestLabel.setText(stock.getHighestPrice().toString());
    lowestLabel.setText(stock.getLowestPrice().toString());

    updateChangeStyle(stock.getCurrentChange());

    chart.clearData();
    int week = 0;
    for (BigDecimal price : stock.getPrices()) {
      chart.addData(week, price);
      week++;
    }
  }
}
