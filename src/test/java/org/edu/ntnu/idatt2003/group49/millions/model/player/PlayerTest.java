package org.edu.ntnu.idatt2003.group49.millions.model.player;

import org.edu.ntnu.idatt2003.group49.millions.model.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.PurchaseCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  Player player;
  BigDecimal startingMoney;

  @BeforeEach
  void setUp() {
    startingMoney = new BigDecimal("10000");
    player = new Player("Steve", startingMoney);
  }

  @Test
  void constructor_ThrowsWhenNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Player(null, startingMoney));
  }

  @Test
  void constructor_ThrowsWhenNameIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> new Player("", startingMoney));
  }

  @Test
  void constructor_ThrowsWhenStartingMoneyIsNull() {
    assertThrows(NullPointerException.class, () -> new Player("Steve", null));
  }

  @Test
  void constructor_ThrowsWhenStartingMoneyIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new Player("Steve", new BigDecimal("-10")));
  }

  @Test
  void addMoney_ThrowsWhenAmountIsNull() {
    assertThrows(NullPointerException.class, () -> player.addMoney(null));
  }

  @Test
  void addMoney_ThrowsWhenAmountIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> player.addMoney(new BigDecimal("-10")));
  }

  @Test
  void addMoney_AddsMoneyToThePlayerMoney() {
    BigDecimal amount = new BigDecimal("100");
    BigDecimal curMoney = player.getMoney();

    player.addMoney(amount);

    assertEquals(curMoney.add(amount), player.getMoney());
  }

  @Test
  void withdrawMoney_ThrowsWhenAmountIsNull() {
    assertThrows(NullPointerException.class, () -> player.withdrawMoney(null));
  }

  @Test
  void withdrawMoney_ThrowsWhenAmountIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> player.withdrawMoney(new BigDecimal("-10")));
  }

  @Test
  void withdrawMoney_ThrowsWhenPlayerHasInsufficientFunds() {
    BigDecimal amount = new BigDecimal("10000000");

    assertThrows(IllegalStateException.class, () -> player.withdrawMoney(amount));
  }

  @Test
  void withdrawMoney_SubtractsWithdrawnFundsFromThePlayerMoney() {
    BigDecimal amount = new BigDecimal("5000");
    BigDecimal curMoney = player.getMoney();

    player.withdrawMoney(amount);

    assertEquals(curMoney.subtract(amount), player.getMoney());
  }

  @Test
  void getStatus_returnsInvestorIfWeeksTraded10OrHigherAndNetWorthIsGreaterThanThreshold() {
    Stock stock = new Stock("APPL", "Apple", new BigDecimal("10"));
    Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("10"));

    for(int i = 0; i < 11; i++) {
      stock.addNewSalesPrice(new BigDecimal("100"));

      new Purchase(share, i, new PurchaseCalculator(share)).commit(player);
    }

    assertEquals(Status.INVESTOR, player.getStatus());
  }

  @Test
  void getStatus_returnsSpeculatorIfWeeksTraded20OrHigherAndNetWorthIsGreaterThanThreshold() {
    Stock stock = new Stock("APPL", "Apple", new BigDecimal("10"));
    Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("10"));

    for(int i = 0; i < 21; i++) {
      stock.addNewSalesPrice(new BigDecimal("200"));

      new Purchase(share, i, new PurchaseCalculator(share)).commit(player);
    }

    assertEquals(Status.SPECULATOR, player.getStatus());
  }

  @Test
  void getStatus_returnsNoviceIfWeeksTraded10OrHigherAndNetWorthIsLessThanThreshold() {
    Stock stock = new Stock("APPL", "Apple", new BigDecimal("10"));
    Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("10"));
    for(int i = 0; i < 11; i++) {
      stock.addNewSalesPrice(new BigDecimal("12"));
      new Purchase(share, i, new PurchaseCalculator(share)).commit(player);
    }

    assertEquals(Status.NOVICE, player.getStatus());
  }
}