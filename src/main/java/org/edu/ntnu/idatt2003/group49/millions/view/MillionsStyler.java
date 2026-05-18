package org.edu.ntnu.idatt2003.group49.millions.view;

import javafx.scene.control.Label;

import java.math.BigDecimal;

public class MillionsStyler {
  public MillionsStyler() {}

  public static void updateChangeStyle(BigDecimal change, Label changeLabel) {
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
