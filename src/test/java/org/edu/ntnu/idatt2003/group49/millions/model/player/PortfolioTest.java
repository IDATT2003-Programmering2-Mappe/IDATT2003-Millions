package org.edu.ntnu.idatt2003.group49.millions.model.player;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.SaleAllocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

  Portfolio portfolio;
  Share share;

  @BeforeEach
  void setUp() {
    share = new Share(
        new Stock("APPL", "Apple", new BigDecimal("263")),
        new BigDecimal("50"),
        new BigDecimal("100")
    );
    portfolio = new Portfolio();
    portfolio.addShare(share);
  }

  @Test
  void addShare_AddsShareToCurrentShares() {
    Share newShare = new Share(
        new Stock("NVDA", "Nvidia", new BigDecimal("180")),
        new BigDecimal("20"),
        new BigDecimal("180")
    );
    portfolio.addShare(newShare);
    assertTrue(portfolio.getShares().contains(share));
  }

  @Test
  void addShare_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> {portfolio.addShare(null);});
  }

  @Test
  void removeShare_RemovesShareFromCurrentShares() {
    portfolio.removeShare(share);
    assertFalse(portfolio.getShares().contains(share));
  }

  @Test
  void removeShare_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> {portfolio.removeShare(null);});
  }

  @Test
  void getShares_ReturnsOnlySharesWithGivenSymbol() {

    Share newShare = new Share(
        new Stock("NVDA", "Nvidia", new BigDecimal("180")),
        new BigDecimal("20"),
        new BigDecimal("180")
    );

    portfolio.addShare(newShare);

    List<Share> result = portfolio.getShares("APPL");

    assertFalse(result.isEmpty());
    assertTrue(
        result.stream()
            .allMatch(s -> s.getStock().getSymbol().equals("APPL")));
  }

  @Test
  void getShares_ThrowsWhenSymbolIsNull() {
    assertThrows(NullPointerException.class, () -> {portfolio.getShares(null);});
  }

  @Test
  void contains_ThrowsWhenShareIsNull() {
    assertThrows(NullPointerException.class, () -> {portfolio.contains(null);});
  }


  @Test
  void reduceShare_ThrowsWhenSellingMoreThanOwned() {
    assertThrows(IllegalArgumentException.class,
            () -> portfolio.reduceShare(share, new BigDecimal("51")));
  }

  @Test
  void reduceShare_RemovesShareWhenSellingAllShares() {
    portfolio.reduceShare(share, new BigDecimal("50"));

    assertFalse(portfolio.contains(share));
  }

  @Test
  void reduceShare_ReplacesShareWithRemainingQuantityAfterPartialSale() {
    portfolio.reduceShare(share, new BigDecimal("20"));

    Share remainingShare = portfolio.getShares().getFirst();

    assertEquals(0, new BigDecimal("30").compareTo(remainingShare.getQuantity()));
    assertEquals(share.getStock(),remainingShare.getStock());
  }

  @Test
  void planSale_ReturnsMultipleAllocationsWhenQuantityExceedsFirstShare() {
    Stock stock = new Stock("NVDA", "Nvidia", new BigDecimal("100"));

    Share firstShare = new Share(stock, BigDecimal.TEN, new BigDecimal("100"));
    Share secondShare = new Share(stock, BigDecimal.TEN, new BigDecimal("150"));

    portfolio.addShare(firstShare);
    portfolio.addShare(secondShare);

    List<SaleAllocation> allocations = portfolio.planSale(stock.getSymbol(), new BigDecimal("15"));

    assertEquals(2, allocations.size());

    assertEquals(firstShare, allocations.get(0).getShare());
    assertEquals(0, BigDecimal.TEN.compareTo(allocations.get(0).getQuantity()));

    assertEquals(secondShare, allocations.get(1).getShare());
    assertEquals(0, new BigDecimal("5").compareTo(allocations.get(1).getQuantity()));
  }

  @Test
  void planSale_ThrowsWhenQuantityExceedsTotalOwnedShares() {
    Stock stock = new Stock("NVDA", "Nvidia", new BigDecimal("100"));

    Share firstShare = new Share(stock, BigDecimal.TEN, new BigDecimal("100"));
    Share secondShare = new Share(stock, BigDecimal.TEN, new BigDecimal("150"));

    portfolio.addShare(firstShare);
    portfolio.addShare(secondShare);

    assertThrows(IllegalArgumentException.class,
            () -> portfolio.planSale(stock.getSymbol(), new BigDecimal("30")));
  }

  @Test
  void planSale_DoesNotChangePortfolioWhenQuantityExceedsTotalOwnedShares() {
    Stock stock = new Stock("NVDA", "Nvidia", new BigDecimal("100"));

    Share firstShare = new Share(stock, BigDecimal.TEN, new BigDecimal("100"));
    Share secondShare = new Share(stock, BigDecimal.TEN, new BigDecimal("150"));

    portfolio.addShare(firstShare);
    portfolio.addShare(secondShare);

    assertThrows(IllegalArgumentException.class,
            () -> portfolio.planSale(stock.getSymbol(), new BigDecimal("30")));

    assertTrue(portfolio.contains(firstShare));
    assertTrue(portfolio.contains(secondShare));
    assertEquals(0, BigDecimal.TEN.compareTo(firstShare.getQuantity()));
    assertEquals(0, BigDecimal.TEN.compareTo(secondShare.getQuantity()));
  }

  @Test
  void applySale_RemovesFirstShareAndReducesSecondShare() {
    Stock stock = new Stock("NVDA", "Nvidia", new BigDecimal("100"));

    Share firstShare = new Share(stock, BigDecimal.TEN, new BigDecimal("100"));
    Share secondShare = new Share(stock, BigDecimal.TEN, new BigDecimal("150"));

    portfolio.addShare(firstShare);
    portfolio.addShare(secondShare);

    List<SaleAllocation> allocations =
            portfolio.planSale(stock.getSymbol(), new BigDecimal("15"));

    portfolio.applySale(allocations);

    List<Share> remainingShares = portfolio.getShares(stock.getSymbol());

    assertEquals(1, remainingShares.size());
    assertEquals(0, new BigDecimal("5").compareTo(remainingShares.getFirst().getQuantity()));
    assertEquals(0, new BigDecimal("150").compareTo(remainingShares.getFirst().getPurchasePrice()));
  }


}



