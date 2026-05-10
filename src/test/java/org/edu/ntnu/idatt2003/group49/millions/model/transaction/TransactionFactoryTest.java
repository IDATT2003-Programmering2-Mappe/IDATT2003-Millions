package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

  private TransactionFactory transactionFactory;
  private Stock nvidiaStock;
  private Share ownedShare;

  @BeforeEach
  void setUp() {
    transactionFactory = new TransactionFactory();
    nvidiaStock = new Stock("NVDA", "Nvidia", new BigDecimal("191.27"));
    ownedShare = new Share(nvidiaStock, new BigDecimal("10"), new BigDecimal("150.00"));
  }

  @Test
  void createPurchase_ThrowsWhenStockIsNull() {
    assertThrows(NullPointerException.class,
            () -> transactionFactory.createPurchase(null, new BigDecimal("2"), 1));
  }

  @Test
  void createPurchase_ThrowsWhenQuantityIsNull() {
    assertThrows(NullPointerException.class,
            () -> transactionFactory.createPurchase(nvidiaStock, null, 1));

  }

  @Test
  void createPurchase_ThrowsWhenQuantityIsZero() {
    assertThrows(IllegalArgumentException.class,
            () -> transactionFactory.createPurchase(nvidiaStock, BigDecimal.ZERO, 1));
  }

  @Test
  void createPurchase_ThrowsWhenQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class,
            () -> transactionFactory.createPurchase(nvidiaStock, new BigDecimal("-1"), 1));
  }

  @Test
  void createPurchase_ThrowsWhenWeekIsNegative() {
    assertThrows(IllegalArgumentException.class,
            () -> transactionFactory.createPurchase(nvidiaStock, new BigDecimal("2"), -1));
  }


  @Test
  void createPurchase_ReturnPurchaseWithCorrectValues() {
    Transaction transaction = transactionFactory.createPurchase(nvidiaStock, new BigDecimal("10"), 1);

    assertAll(
            () -> assertInstanceOf(Purchase.class, transaction),
            () -> assertInstanceOf(PurchaseCalculator.class, transaction.getCalculator()),
            () -> assertEquals(nvidiaStock, transaction.getShare().getStock()),
            () -> assertEquals(0, new BigDecimal("10").compareTo(transaction.getShare().getQuantity())),
            () -> assertEquals(1, transaction.getWeek()),
            () -> assertEquals(0, nvidiaStock.getSalesPrice().compareTo(transaction.getShare().getPurchasePrice()))
    );
  }

  @Test
  void createSale_ThrowsWhenOwnedShareIsNull() {
    assertThrows(NullPointerException.class,
            () -> transactionFactory.createSale(null, new BigDecimal("2"), 1));
  }

  @Test
  void createSale_ThrowsWhenQuantityToSellIsNull() {
    assertThrows(NullPointerException.class,
            () -> transactionFactory.createSale(ownedShare, null, 1));
  }

  @Test
  void createSale_ThrowsWhenQuantityToSellIsNegative() {
    assertThrows(IllegalArgumentException.class,
            () -> transactionFactory.createSale(ownedShare, new BigDecimal("-1"), 1));
  }

  @Test
  void createSale_ThrowsWhenQuantityToSellIsGreaterThanOwnedQuantity() {
    assertThrows(IllegalArgumentException.class,
            () -> transactionFactory.createSale(ownedShare, new BigDecimal("11"), 1));
  }

  @Test
  void createSale_ThrowsWhenWeekIsNegative() {
    assertThrows(IllegalArgumentException.class,
            () -> transactionFactory.createSale(ownedShare, new BigDecimal("2"), -1));
  }


  @Test
  void createSale_ReturnsSaleTransactionWithCorrectValues() {
    Transaction transaction = transactionFactory.createSale(ownedShare, new BigDecimal("3"), 1);

    assertAll(
            () -> assertInstanceOf(Sale.class, transaction),
            () -> assertInstanceOf(SaleCalculator.class, transaction.getCalculator()),
            () -> assertEquals(1, transaction.getWeek()),
            () -> assertEquals(ownedShare.getStock(), transaction.getShare().getStock()),
            () -> assertEquals(0, new BigDecimal("3").compareTo(transaction.getShare().getQuantity())),
            () -> assertEquals(0, ownedShare.getPurchasePrice().compareTo(transaction.getShare().getPurchasePrice()))
    );
  }

}