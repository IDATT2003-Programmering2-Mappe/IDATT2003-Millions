package org.edu.ntnu.idatt2003.group49.millions.view.landingpage;

import org.edu.ntnu.idatt2003.group49.millions.controller.Navigator;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class LandingPageView extends MillionsView {
  private final navigator;

  public LandingPageView(Navigator navigator) {
    this.navigator = navigator;

    getStylesheets().add(Objects.requireNonNull(
            getClass().getResource("/styles/landingpage.css")
    ).toExternalForm());
    getChildren().add(build());
  }
    @Override
    protected Pane build() {
  }
