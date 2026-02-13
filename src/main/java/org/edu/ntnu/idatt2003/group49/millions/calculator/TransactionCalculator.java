package org.edu.ntnu.idatt2003.group49.millions.calculator;

import java.math.BigDecimal;

public interface TransactionCalculator {
  BigDecimal calculateGross();

  BigDecimal calculateCommission();

  BigDecimal calculateTax();

  BigDecimal calculateTotal();
}
