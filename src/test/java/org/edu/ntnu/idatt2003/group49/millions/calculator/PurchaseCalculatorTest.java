package org.edu.ntnu.idatt2003.group49.millions.calculator;

import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseCalculatorTest {

  TransactionCalculator purchaseCalculator;
  Share share;
  Stock stock;
  BigDecimal quantity;
  BigDecimal purchasePrice;

  @BeforeEach
  void setUp() {
    stock         = new Stock("APPL", "Apple", new BigDecimal("260"));
    quantity      = new BigDecimal("50");
    purchasePrice = new BigDecimal("260");

    share = new Share(stock, quantity, purchasePrice);
    purchaseCalculator = new PurchaseCalculator(share);
  }

  @Test
  void constructor_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> new PurchaseCalculator(null));
  }

  @Test
  void calculateGross_ReturnsPurchasePriceTimesQuantity() {
    // Arrange
    BigDecimal expectedGross = purchasePrice.multiply(quantity);

    // Act
    BigDecimal gross = purchaseCalculator.calculateGross();

    // Assert
    assertEquals(0, gross.compareTo(expectedGross));
  }

  @Test
  void calculateCommission_Returns() {
    // Arrange
    BigDecimal expectedGross = purchasePrice.multiply(quantity);
    BigDecimal commissionRate = new BigDecimal("0.005");
    BigDecimal expectedCommission = expectedGross.multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);

    // Act
    BigDecimal commission = purchaseCalculator.calculateCommission();

    // Assert
    assertEquals(0, commission.compareTo(expectedCommission));
  }

  @Test
  void calculateTax_ReturnsZeroPercentTax() {
    assertEquals(BigDecimal.ZERO, purchaseCalculator.calculateTax());
  }

  @Test
  void calculateTotal() {
    // Arrange
    BigDecimal expectedGross = purchasePrice.multiply(quantity);
    BigDecimal commissionRate = new BigDecimal("0.005");
    BigDecimal expectedCommission = expectedGross.multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);
    BigDecimal expectedTax = BigDecimal.ZERO;
    BigDecimal expectedTotal = expectedGross.add(expectedCommission).add(expectedTax);

    // Act
    BigDecimal total = purchaseCalculator.calculateTotal();

    // Assert
    assertEquals(0, total.compareTo(expectedTotal));
  }
}