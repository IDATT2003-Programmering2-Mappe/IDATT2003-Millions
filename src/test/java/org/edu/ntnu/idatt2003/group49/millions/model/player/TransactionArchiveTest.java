package org.edu.ntnu.idatt2003.group49.millions.model.player;

import org.edu.ntnu.idatt2003.group49.millions.model.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Purchase;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Sale;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionArchiveTest {

  private TransactionArchive transactionArchive;
  private BigDecimal quantity;
  private BigDecimal purchasePrice;
  private BigDecimal salesPrice;
  private Stock stock;

  @BeforeEach
  void setUp() {
    purchasePrice = new BigDecimal("100");
    quantity = new BigDecimal("10");
    salesPrice = new BigDecimal("300");
    stock = new Stock("APPL", "Apple", salesPrice);

    transactionArchive = new TransactionArchive();
  }

  @Test
  void add_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> transactionArchive.add(null));
  }

  @Test
  void add_AddsTransactionToArchive() {
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase = new Purchase(share, 1, new PurchaseCalculator(share));

    transactionArchive.add(purchase);

    assertTrue(transactionArchive.getTransactions(1).contains(purchase));
  }

  @Test
  void getTransactions_ThrowsWhenWeekIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> transactionArchive.getTransactions(-1));
  }

  @Test
  void getTransactions_ThrowsWhenThereAreNoTransactionsInTheGivenWeek() {
    assertThrows(IllegalArgumentException.class, () -> transactionArchive.getTransactions(200));
  }

  @Test
  void getTransactions_OnlyReturnsTransactionsInTheGivenWeek() {
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase1 = new Purchase(share, 1, new PurchaseCalculator(share));
    Purchase purchase2 = new Purchase(share, 1, new PurchaseCalculator(share));
    Purchase purchase3 = new Purchase(share, 33, new PurchaseCalculator(share));

    transactionArchive.add(purchase1);
    transactionArchive.add(purchase2);
    transactionArchive.add(purchase3);
    List<Transaction> transactions = transactionArchive.getTransactions(1);

    assertFalse(transactions.isEmpty());
    assertTrue(transactions.stream().allMatch(t -> t.getWeek() == 1));
  }

  @Test
  void getPurchases_ThrowsWhenWeekIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> transactionArchive.getPurchases(-1));
  }

  @Test
  void getPurchases_ThrowsWhenThereAreNoPurchasesInTheGivenWeek() {
    assertThrows(IllegalArgumentException.class, () -> transactionArchive.getPurchases(200));
  }

  @Test
  void getPurchases_OnlyReturnsPurchasesInTheGivenWeek() {
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase1 = new Purchase(share, 1, new PurchaseCalculator(share));
    Purchase purchase2 = new Purchase(share, 33, new PurchaseCalculator(share));
    Sale sale = new Sale(share, 1, new SaleCalculator(share));

    transactionArchive.add(purchase1);
    transactionArchive.add(purchase2);
    transactionArchive.add(sale);
    List<Purchase> transactions = transactionArchive.getPurchases(1);

    assertFalse(transactions.isEmpty());
    assertTrue(transactions.stream().allMatch(t -> t.getWeek() == 1));
  }

  @Test
  void getPurchases_OnlyReturnsPurchases() {
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase1 = new Purchase(share, 1, new PurchaseCalculator(share));
    Purchase purchase2 = new Purchase(share, 1, new PurchaseCalculator(share));
    Sale sale = new Sale(share, 1, new SaleCalculator(share));

    transactionArchive.add(purchase1);
    transactionArchive.add(purchase2);
    transactionArchive.add(sale);
    List<Purchase> transactions = transactionArchive.getPurchases(1);

    assertFalse(transactions.isEmpty());
    assertTrue(transactions.stream().allMatch(t -> t instanceof Purchase));
  }

  @Test
  void getSales_ThrowsWhenWeekIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> transactionArchive.getSales(-1));
  }

  @Test
  void getSales_ThrowsWhenThereAreNoSalesInTheGivenWeek() {
    assertThrows(IllegalArgumentException.class, () -> transactionArchive.getSales(200));
  }

  @Test
  void getSales_OnlyReturnsSalesInTheGivenWeek() {
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase1 = new Purchase(share, 1, new PurchaseCalculator(share));
    Purchase purchase2 = new Purchase(share, 33, new PurchaseCalculator(share));
    Sale sale1 = new Sale(share, 1, new SaleCalculator(share));
    Sale sale2 = new Sale(share, 1, new SaleCalculator(share));

    transactionArchive.add(purchase1);
    transactionArchive.add(purchase2);
    transactionArchive.add(sale1);
    transactionArchive.add(sale2);
    List<Sale> transactions = transactionArchive.getSales(1);

    assertFalse(transactions.isEmpty());
    assertTrue(transactions.stream().allMatch(t -> t.getWeek() == 1));
  }

  @Test
  void getSales_OnlyReturnsSales() {
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase1 = new Purchase(share, 1, new PurchaseCalculator(share));
    Purchase purchase2 = new Purchase(share, 1, new PurchaseCalculator(share));
    Sale sale = new Sale(share, 1, new SaleCalculator(share));

    transactionArchive.add(purchase1);
    transactionArchive.add(purchase2);
    transactionArchive.add(sale);
    List<Sale> transactions = transactionArchive.getSales(1);

    assertFalse(transactions.isEmpty());
    assertTrue(transactions.stream().allMatch(t -> t instanceof Sale));
  }

  @Test
  void countDistinctWeeks_ReturnsDistinctWeeks() {
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase1 = new Purchase(share, 1, new PurchaseCalculator(share));
    Purchase purchase2 = new Purchase(share, 2, new PurchaseCalculator(share));
    Purchase purchase3 = new Purchase(share, 2, new PurchaseCalculator(share));
    Sale sale1 = new Sale(share, 3, new SaleCalculator(share));
    Sale sale2 = new Sale(share, 3, new SaleCalculator(share));

    transactionArchive.add(purchase1);
    transactionArchive.add(purchase2);
    transactionArchive.add(purchase3);
    transactionArchive.add(sale1);
    transactionArchive.add(sale2);
    int distinctWeeks = transactionArchive.countDistinctWeeks();

    assertEquals(3, distinctWeeks);
  }
}