package org.edu.ntnu.idatt2003.group49.millions.transaction;

import org.edu.ntnu.idatt2003.group49.millions.Player;
import org.edu.ntnu.idatt2003.group49.millions.Share;
import org.edu.ntnu.idatt2003.group49.millions.Stock;
import org.edu.ntnu.idatt2003.group49.millions.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.calculator.TransactionCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {
  TransactionCalculator purchaseCalculator;
  BigDecimal quantity;
  BigDecimal purchasePrice;
  BigDecimal salesPrice;
  Stock stock;
  Share share;
  Player player;
  Purchase purchase;

  @BeforeEach
  void setUp() {
    quantity = new BigDecimal("50");
    purchasePrice = new BigDecimal("100");
    salesPrice = new BigDecimal("300");
    stock = new Stock("APPL", "Apple", salesPrice);
    share = new Share(stock, quantity, purchasePrice);
    purchaseCalculator = new PurchaseCalculator(share);
    player = new Player("Steve", new BigDecimal("10000"));
    purchase = new Purchase(share, 1, purchaseCalculator);
  }

  @Test
  void constructor_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> new Purchase(null, 1, purchaseCalculator));
  }

  @Test
  void constructor_ThrowsWhenCalculatorIsNull() {
    assertThrows(NullPointerException.class, () -> new Purchase(share, 1, null));
  }

  @Test
  void constructor_ThrowsWhenWeekIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new Purchase(share, -1, purchaseCalculator));
  }

  @Test
  void commit_WithdrawsMoneyFromPlayer() {
    BigDecimal moneyBefore = player.getMoney();
    BigDecimal totalCost = purchaseCalculator.calculateTotal();

    purchase.commit(player);

    assertEquals(moneyBefore.subtract(totalCost), player.getMoney());
  }

  @Test
  void commit_AddsShareToPlayersPortfolio() {
    purchase.commit(player);
    assertTrue(player.getPortfolio().contains(share));
  }
  @Test
  void commit_StoresTransactionInArchive() {
    purchase.commit(player);

    assertEquals(1, player.getTransactionArchive().getTransactions(1).size());
  }
//negative tester
  @Test
  void commit_ThrowsWhenPlayerIsNull() {
    assertThrows(NullPointerException.class, () -> purchase.commit(null));
  }

  @Test
  void commit_ThrowsWhenTransactionIsAlreadyCommitted() {
    purchase.commit(player);
    assertThrows(IllegalStateException.class, () -> purchase.commit(player));
  }
  @Test
  void commit_ThrowsWhenNotEnoughMoney() {
    Player poorPlayer = new Player("Poor", new BigDecimal("1"));
    assertThrows(IllegalStateException.class, () -> purchase.commit(poorPlayer));
  }
}