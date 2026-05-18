package org.edu.ntnu.idatt2003.group49.millions.view.pages.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.edu.ntnu.idatt2003.group49.millions.controller.ExchangeController;
import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.view.StockObserver;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsChart.MillionsChart;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.SharesTable;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.TableSelectionModel;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory.SharesColumnFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DashboardView extends MillionsView implements StockObserver {
  private final Navigator navigator;
  private final ExchangeController exchangeController;
  private final PlayerController playerController;

  private final PortfolioInfo portfolioInfo;
  private final MillionsChart chart;
  private final OwnedShares ownedShares;
  private final SharesTable sharesTable;
  private final TableSelectionModel<Share> selectionModel;

  private final ObservableList<BigDecimal> stockData = FXCollections.observableArrayList();

  public DashboardView(Navigator navigator, ExchangeController exchangeController, PlayerController playerController, TableSelectionModel<Share> selectionModel) {
    this.navigator = navigator;
    this.exchangeController = exchangeController;
    this.playerController = playerController;
    this.selectionModel = selectionModel;
    this.portfolioInfo = new PortfolioInfo("Portfolio", playerController.getCurrentPortfolioValue(), playerController.getPortfolioChange());
    this.chart = new MillionsChart();
    this.sharesTable = new SharesTable(new SharesColumnFactory(), selectionModel);
    this.ownedShares = new OwnedShares(sharesTable);

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    HBox body = new HBox();
    body.getStyleClass().add("dashboard");

    body.getChildren().addAll(
      createLeftBody(),
      createRightBody()
    );

    Map<Integer, BigDecimal> values = playerController.getPortfolioValues();
    values.forEach(chart::addData);

    List<Integer> keys = playerController.getPortfolioValues().keySet().stream().toList();
    keys.forEach(key -> {
      chart.addData(key, playerController.getPortfolioValues().get(key));
    });
    values.forEach(chart::addData);

    chart.updateYAxis(playerController.getHighestPortfolioValue(), playerController.getLowestPortfolioValue());

    return body;
  }

  private VBox createLeftBody() {
    Button advanceBtn = new Button("Advance");
    advanceBtn.getStyleClass().add("advance-btn");

    advanceBtn.setOnAction(e -> {
      exchangeController.advance();
    });

    HBox controls = new HBox();
    controls.getStyleClass().add("controls");

    controls.getChildren().addAll(advanceBtn);

    VBox leftBody = new VBox();
    leftBody.getStyleClass().add("body-left");

    leftBody.setFillWidth(true);
    HBox.setHgrow(leftBody, Priority.ALWAYS);

    leftBody.getChildren().addAll(
      portfolioInfo,
      chart,
      controls
    );
    return leftBody;
  }

  private VBox createRightBody() {
    VBox rightBody = new VBox();
    rightBody.getStyleClass().add("body-right");
    rightBody.setFillWidth(true);
    HBox.setHgrow(rightBody, Priority.ALWAYS);

    rightBody.getChildren().add(
      ownedShares
    );
    return rightBody;
  }

  @Override
  public void update(Map<String, Stock> stockMap, int week) {
    chart.addData(week, playerController.getCurrentPortfolioValue());
    chart.updateYAxis(playerController.getHighestPortfolioValue(), playerController.getLowestPortfolioValue());
    portfolioInfo.updateInfoBar("Portfolio", playerController.getCurrentPortfolioValue(), playerController.getPortfolioChange());
  }
}
