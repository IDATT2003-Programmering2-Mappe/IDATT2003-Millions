package org.edu.ntnu.idatt2003.group49.millions.view.pages.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsStyler;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.MillionsTable;
import org.edu.ntnu.idatt2003.group49.millions.view.StockObserver;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.SharesTable;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.TableSelectionModel;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory.SharesColumnFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DashboardView extends MillionsView implements StockObserver {
  private final ExchangeController exchangeController;
  private final PlayerController playerController;

  private final MillionsChart chart;
  private final MillionsTable<Share> table;

  private Label nameLabel;
  private Label valueLabel;
  private Label changeLabel;

  private final ObservableList<BigDecimal> stockData = FXCollections.observableArrayList();

  public DashboardView(ExchangeController exchangeController, PlayerController playerController) {
    this.exchangeController = exchangeController;
    this.playerController = playerController;

    this.chart = new MillionsChart();
    this.table = new SharesTable(new SharesColumnFactory(), new TableSelectionModel<>());

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    HBox body = new HBox(createLeftBody(), createRightBody());
    body.getStyleClass().add("dashboard");

    Map<Integer, BigDecimal> valueMap = playerController.getPortfolioValueMap();
    valueMap.forEach(chart::addData);

    List<Integer> keys = playerController.getPortfolioValueMap().keySet().stream().toList();
    keys.forEach(key -> {
      chart.addData(key, playerController.getPortfolioValueMap().get(key));
    });

    Collection<BigDecimal> values = playerController.getPortfolioValueMap().values();
    List<BigDecimal> filteredValues = values.stream().filter(v -> v.compareTo(BigDecimal.ZERO) != 0).toList();

    if (!filteredValues.isEmpty()) {
      valueMap.forEach(chart::addData);
    }

    chart.updateYAxis(playerController.getHighestPortfolioValue(), playerController.getLowestPortfolioValue());

    return body;
  }

  private VBox createLeftBody() {
    Button advanceBtn = new Button("Advance");
    advanceBtn.getStyleClass().add("advance-btn");

    advanceBtn.setOnAction(e -> {
      playerController.addValue(exchangeController.getWeek());
      exchangeController.advance();
    });

    HBox controls = new HBox(advanceBtn);
    controls.getStyleClass().add("controls");

    VBox leftBody = new VBox(createInfoBar(), chart, controls);
    leftBody.getStyleClass().add("body-left");

    HBox.setHgrow(leftBody, Priority.ALWAYS);

    return leftBody;
  }

  private VBox createRightBody() {
    VBox rightBody = new VBox(createOwnedShares());
    rightBody.getStyleClass().add("body-right");

    HBox.setHgrow(rightBody, Priority.ALWAYS);

    return rightBody;
  }

  private HBox createInfoBar() {
    nameLabel = new Label("Portfolio");
    valueLabel = new Label(playerController.getCurrentPortfolioValue().toString());
    changeLabel = new Label(playerController.getPortfolioChange().toString() + "%");

    nameLabel.getStyleClass().add("name");
    valueLabel.getStyleClass().add("price");
    changeLabel.getStyleClass().add("change");

    MillionsStyler.updateChangeStyle(playerController.getPortfolioChange().doubleValue(), changeLabel);

    HBox valueSection = new HBox(valueLabel, changeLabel);
    valueSection.getStyleClass().add("value-section");

    HBox infoBar = new HBox(nameLabel, valueSection);
    infoBar.getStyleClass().add("info-bar");

    nameLabel.setMaxWidth(Double.MAX_VALUE);

    HBox.setHgrow(nameLabel, Priority.ALWAYS);

    return infoBar;
  }

  private VBox createOwnedShares() {
    Label ownedSharesLabel = new Label("Owned Shares");
    ownedSharesLabel.getStyleClass().add("owned-shares-label");

    HBox title = new HBox(ownedSharesLabel);
    title.getStyleClass().add("owned-shares-title");

    table.setItems(playerController.getOwnedShares());

    playerController.getOwnedShares().forEach(s -> System.out.println(s.toString()));

    VBox vBox = new VBox(title, table);
    vBox.getStyleClass().add("owned-shares");
    vBox.setSpacing(5);

    return vBox;
  }

  public void updateInfoBar() {
    nameLabel = new Label("Portfolio");
    valueLabel = new Label(playerController.getCurrentPortfolioValue().toString());
    changeLabel = new Label(playerController.getPortfolioChange().toString() + "%");

    MillionsStyler.updateChangeStyle(playerController.getPortfolioChange().doubleValue(), changeLabel);
  }

  @Override
  public void update(Map<String, Stock> stockMap, int week) {
    chart.addData(week, playerController.getCurrentPortfolioValue());
    chart.updateYAxis(playerController.getHighestPortfolioValue(), playerController.getLowestPortfolioValue());
    updateInfoBar();
  }
}
