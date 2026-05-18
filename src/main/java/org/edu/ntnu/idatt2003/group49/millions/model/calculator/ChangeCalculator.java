package org.edu.ntnu.idatt2003.group49.millions.model.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChangeCalculator {
  public ChangeCalculator() {}

  // TODO: make tests for this
  public static BigDecimal calculatePercentageChange(BigDecimal startPrice, BigDecimal currentPrice) {
    if (startPrice.compareTo(BigDecimal.ZERO) == 0) {
      throw new IllegalArgumentException("startPrice cannot be zero");
    }

    return currentPrice
      .subtract(startPrice)
      .divide(startPrice, 4, RoundingMode.HALF_UP)
      .multiply(BigDecimal.valueOf(100));
  }
}
