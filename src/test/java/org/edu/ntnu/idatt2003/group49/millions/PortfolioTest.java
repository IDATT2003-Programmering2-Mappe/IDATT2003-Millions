package org.edu.ntnu.idatt2003.group49.millions;

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
}