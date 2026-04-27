package org.edu.ntnu.idatt2003.group49.millions.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.TransactionCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

  TransactionCalculator saleCalculator;
  BigDecimal quantity;
  BigDecimal purchasePrice;
  BigDecimal salesPrice;
  Stock stock;
  Share share;
  Player player;
  Sale sale;

  @BeforeEach
  void setUp() {
    quantity = new BigDecimal("50");
    purchasePrice = new BigDecimal("100");
    salesPrice = new BigDecimal("300");
    stock = new Stock("APPL", "Apple", salesPrice);
    share = new Share(stock, quantity, purchasePrice);
    saleCalculator = new SaleCalculator(share);
    player = new Player("Steve", new BigDecimal("100"));
    sale = new Sale(share, 1, saleCalculator);
  }

  @Test
  void constructor_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> new Sale(null, 1, saleCalculator));
  }

  @Test
  void constructor_ThrowsWhenCalculatorIsNull() {
    assertThrows(NullPointerException.class, () -> new Sale(share, 1, null));
  }

  @Test
  void constructor_ThrowsWhenWeekIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new Sale(share, -1, saleCalculator));
  }

  @Test
  void commit_ThrowsWhenPlayerIsNull() {

    assertThrows(NullPointerException.class, () -> sale.commit(null));
  }

  @Test
  void commit_ThrowsWhenTransactionIsAlreadyCommited() {
    sale.commit(player);

    assertThrows(IllegalArgumentException.class, () -> sale.commit(player));
  }

  @Test
  void commit_ThrowsWhenPlayerDoesNotOwnShare() {
    sale.commit(player);

    assertThrows(IllegalArgumentException.class, () -> sale.commit(player));
  }

  @Test
  void commit_AddsSalesRevenueToPlayersMoney() {
    BigDecimal prevPlayerMoney = player.getMoney();
    sale.commit(player);

    BigDecimal salesRevenue = sale.getCalculator().calculateTotal();

    assertEquals(prevPlayerMoney.add(salesRevenue), player.getMoney());
  }

  @Test
  void commit_RemovesShareFromPlayerPortfolio() {
    sale.commit(player);

    assertFalse(player.getPortfolio().contains(share));
  }
}