package org.edu.ntnu.idatt2003.group49.millions.view.pages.tradingpage;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsStyler;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsChart.MillionsChart;

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

  private HBox infoSection;

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
    infoSection = createInfoSection();

    VBox stockInfo = new VBox(new VBox(title(), chart), infoSection);
    stockInfo.getStyleClass().add("stock-info");

    VBox.setVgrow(infoSection, Priority.ALWAYS);

    return stockInfo;
  }

  private HBox title() {
    this.companyLabel = new Label(stock.getCompany());
    companyLabel.getStyleClass().add("company-label");

    HBox title = new HBox();
    title.getStyleClass().add("title");
    title.getChildren().add(companyLabel);

    HBox.setHgrow(companyLabel, Priority.ALWAYS);

    return title;
  }

  private HBox createInfoSection() {
    this.symbolLabel = new Label(stock.getSymbol());
    this.priceLabel = new Label(stock.getSalesPrice().toString());
    this.changeLabel = new Label(stock.getCurrentChange().toString());
    this.highestLabel = new Label(stock.getHighestPrice().toString());
    this.lowestLabel = new Label(stock.getLowestPrice().toString());

    symbolLabel.getStyleClass().addAll("info-label", "symbol-label");
    priceLabel.getStyleClass().addAll("info-label", "price-label");
    changeLabel.getStyleClass().addAll("info-label", "change-label");
    highestLabel.getStyleClass().addAll("info-label", "highest-label");
    lowestLabel.getStyleClass().addAll("info-label", "lowest-label");

    symbolLabel.setMaxWidth(Double.MAX_VALUE);
    priceLabel.setMaxWidth(Double.MAX_VALUE);
    changeLabel.setMaxWidth(Double.MAX_VALUE);
    highestLabel.setMaxWidth(Double.MAX_VALUE);
    lowestLabel.setMaxWidth(Double.MAX_VALUE);


    VBox identifierBox = createIdentifierBox();
    VBox infoBox = createInfoBox();

    HBox.setHgrow(identifierBox, Priority.ALWAYS);
    HBox.setHgrow(infoBox, Priority.ALWAYS);

    HBox infoSection = new HBox(identifierBox, infoBox);
    infoSection.getStyleClass().add("info-section");

    infoSection.setMaxHeight(Double.MAX_VALUE);

    return infoSection;
  }

  private VBox createIdentifierBox() {
    Label symbolIDLabel = new Label("Symbol:");
    Label priceIDLabel = new Label("Current price:");
    Label changeIDLabel = new Label("Change:");
    Label highestIDLabel = new Label("Highest price:");
    Label lowestIDLabel = new Label("Lowest price:");

    symbolIDLabel.getStyleClass().addAll("identifier-label");
    priceIDLabel.getStyleClass().addAll("identifier-label");
    changeIDLabel.getStyleClass().addAll("identifier-label");
    highestIDLabel.getStyleClass().addAll("identifier-label");
    lowestIDLabel.getStyleClass().addAll("identifier-label");

    symbolIDLabel.setMaxWidth(Double.MAX_VALUE);
    priceIDLabel.setMaxWidth(Double.MAX_VALUE);
    changeIDLabel.setMaxWidth(Double.MAX_VALUE);
    highestIDLabel.setMaxWidth(Double.MAX_VALUE);
    lowestIDLabel.setMaxWidth(Double.MAX_VALUE);

    VBox identifierBox = new VBox();

    identifierBox.getStyleClass().add("identifier-box");

    identifierBox.getChildren().addAll(
      symbolIDLabel,
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
      symbolLabel,
      priceLabel,
      changeLabel,
      highestLabel,
      lowestLabel
    );

    return infoBox;
  }

  public void updateStockInfo() {
    companyLabel.setText(stock.getCompany());
    symbolLabel.setText(stock.getSymbol());
    priceLabel.setText(stock.getSalesPrice().toString());
    changeLabel.setText(stock.getCurrentChange().toString());
    highestLabel.setText(stock.getHighestPrice().toString());
    lowestLabel.setText(stock.getLowestPrice().toString());

    MillionsStyler.updateChangeStyle(stock.getCurrentChange().doubleValue(), changeLabel);

    chart.clearData();
    int week = 0;
    for (BigDecimal price : stock.getPrices()) {
      chart.addData(week, price);
      week++;
    }

    chart.updateYAxis(stock.getHighestPrice(), stock.getLowestPrice());
  }
}
