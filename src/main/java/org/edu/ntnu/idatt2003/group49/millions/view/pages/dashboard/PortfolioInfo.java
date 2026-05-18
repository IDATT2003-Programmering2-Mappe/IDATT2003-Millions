package org.edu.ntnu.idatt2003.group49.millions.view.pages.dashboard;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.math.BigDecimal;
import java.util.Objects;

public class PortfolioInfo extends MillionsView {
  private String name;
  private BigDecimal value;
  private BigDecimal change;

  private Label nameLabel;
  private Label valueLabel;
  private Label changeLabel;

  public PortfolioInfo(String name, BigDecimal value, BigDecimal change) {
    this.name = name;
    this.value = value;
    this.change = change;

    getStylesheets().add(Objects.requireNonNull(
      getClass().getResource("/styles/dashboard.css")
    ).toExternalForm());
    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    return createInfoBar();
  }

  private BorderPane createInfoBar() {
    nameLabel = new Label(name);
    valueLabel = new Label(value.toString());
    changeLabel = new Label(change.toString() + "%");

    nameLabel.getStyleClass().add("name");
    valueLabel.getStyleClass().add("price");
    changeLabel.getStyleClass().add("change");

    updateChangeStyle();

    HBox valueSection = new HBox();
    valueSection.getStyleClass().add("value-section");
    valueSection.getChildren().addAll(
      valueLabel,
      changeLabel
    );

    BorderPane infoBar = new BorderPane();
    infoBar.getStyleClass().add("info-bar");
    infoBar.setLeft(nameLabel);
    infoBar.setRight(valueSection);

    return infoBar;
  }

  public void updateInfoBar(String name, BigDecimal value, BigDecimal change) {
    this.name = name;
    this.value = value;
    this.change = change;

    nameLabel.setText(name);
    valueLabel.setText(value.toString());
    changeLabel.setText(change + "%");

    updateChangeStyle();
  }

  private void updateChangeStyle() {
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
}
