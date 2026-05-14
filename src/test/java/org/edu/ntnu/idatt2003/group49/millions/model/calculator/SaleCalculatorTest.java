package org.edu.ntnu.idatt2003.group49.millions.model.calculator;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class SaleCalculatorTest {

  TransactionCalculator saleCalculator;
  Share share;
  Stock stock;
  BigDecimal purchasePrice;
  BigDecimal quantity;
  BigDecimal salesPrice;

  @BeforeEach
  void setUp() {
    stock         = new Stock("APPL", "Apple", new BigDecimal("260"));
    purchasePrice = new BigDecimal("260");
    quantity      = new BigDecimal("50");
    salesPrice    = stock.getSalesPrice();

    share = new Share(stock, quantity, purchasePrice);
    saleCalculator = new SaleCalculator(share);
  }

  @Test
  void constructor_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> new SaleCalculator(null));
  }

  @Test
  void calculateGross_ReturnsSalesPriceTimesQuantity() {
    // Arrange
    BigDecimal expectedGross = salesPrice.multiply(quantity);

    // Act
    BigDecimal gross = saleCalculator.calculateGross();

    // Assert
    assertEquals(expectedGross, gross);
  }

  @Test
  void calculateCommission_ReturnsGrossTimesCommissionRate() {
    // Arrange
    BigDecimal expectedGross = salesPrice.multiply(quantity);
    BigDecimal commissionRate = new BigDecimal("0.01");
    BigDecimal expectedCommission = expectedGross.multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);

    // Act
    BigDecimal commission = saleCalculator.calculateCommission();

    // Assert
    assertEquals(expectedCommission, commission);
  }

  @Test
  void calculateTax_ReturnsThirtyPercentTaxOnGain() {
    // Arrange
    BigDecimal expectedGross = salesPrice.multiply(quantity);
    BigDecimal commissionRate = new BigDecimal("0.01");
    BigDecimal expectedCommission = expectedGross.multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);

    BigDecimal expectedGain = expectedGross.subtract(expectedCommission).subtract(purchasePrice.multiply(quantity));
    BigDecimal taxRate = new BigDecimal("0.30");
    BigDecimal expectedTax = taxRate.multiply(expectedGain);

    // Act
    BigDecimal tax = saleCalculator.calculateTax();

    // Assert
    assertEquals(expectedTax, tax);
  }

  @Test
  void calculateTotal() {
    // Arrange
    BigDecimal expectedGross = salesPrice.multiply(quantity);
    BigDecimal commissionRate = new BigDecimal("0.01");
    BigDecimal expectedCommission = expectedGross.multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);

    BigDecimal expectedGain = expectedGross.subtract(expectedCommission).subtract(purchasePrice.multiply(quantity));
    BigDecimal taxRate = new BigDecimal("0.30");
    BigDecimal expectedTax = taxRate.multiply(expectedGain);
    BigDecimal expectedTotal = expectedGross.subtract(expectedCommission).subtract(expectedTax);

    // Act
    BigDecimal total = saleCalculator.calculateTotal();

    // Assert
    assertEquals(0, total.compareTo(expectedTotal));
  }

  @Test
  void calculateTax_UsesTotalPurchaseCostWhenProvided() {
    Share aggregatedSaleShare = new Share(stock, new BigDecimal("15"), new BigDecimal("100"));

    BigDecimal totalPurchaseCost = new BigDecimal("2000");
    TransactionCalculator calculator = new SaleCalculator(aggregatedSaleShare, totalPurchaseCost);

    BigDecimal expectedGross = stock.getSalesPrice().multiply(new BigDecimal("15"));
    BigDecimal expectedCommission = expectedGross.multiply(new BigDecimal("0.01")).setScale(2, RoundingMode.HALF_UP);

    BigDecimal expectedGain = expectedGross.subtract(expectedCommission).subtract(totalPurchaseCost);

    BigDecimal expectedTax = new BigDecimal("0.30").multiply(expectedGain);

    assertEquals(0, expectedTax.compareTo(calculator.calculateTax()));
  }
}