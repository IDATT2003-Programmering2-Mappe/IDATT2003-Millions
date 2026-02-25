package org.edu.ntnu.idatt2003.group49.millions.transaction;

import org.edu.ntnu.idatt2003.group49.millions.Player;
import org.edu.ntnu.idatt2003.group49.millions.Share;
import org.edu.ntnu.idatt2003.group49.millions.Stock;
import org.edu.ntnu.idatt2003.group49.millions.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.calculator.TransactionCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {
  private Player player;
  private Share share;
  private Purchase purchase;
  private TransactionCalculator calculator;


  @BeforeEach
  void setUp() {
    player = new Player("Steve", new BigDecimal("1000"));

    share = new Share(
            new Stock("APPL", "Apple", new BigDecimal("263")),
            new BigDecimal("5"),
            new BigDecimal("100")
    );
  }
  @Test
  void commit_SetsCommittedTrue() {
    purchase.commit(player);
    assertTrue(purchase.isCommitted());
  }

  @Test
  void commit_ThrowsWhenAlreadyCommitted() {
    purchase.commit(player);
    assertThrows(IllegalStateException.class, () -> purchase.commit(player));
  }

  @Test
  void commit_WithdrawsMoneyFromPlayer() {
    BigDecimal moneyBefore = player.getMoney();
    BigDecimal totalCost = calculator.calculateTotal();

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
}