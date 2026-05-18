package org.edu.ntnu.idatt2003.group49.millions.view;

import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

import java.math.BigDecimal;

public class MillionsStyler {
  public MillionsStyler() {}

  public static void updateChangeStyle(Number change, Labeled changeLabel) {
    changeLabel.getStyleClass().removeAll("positive-change", "negative-change", "zero-change");

    if ((Double) change > 0) {
      changeLabel.setText("+" + change + "%");
      changeLabel.getStyleClass().add("positive-change");
    }
    else if ((Double) change < 0) {
      changeLabel.setText(change + "%");
      changeLabel.getStyleClass().add("negative-change");
    }
    else {
      changeLabel.setText(change + "%");
      changeLabel.getStyleClass().add("zero-change");
    }
  }
}
