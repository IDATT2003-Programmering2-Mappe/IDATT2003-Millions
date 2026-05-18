package org.edu.ntnu.idatt2003.group49.millions.model.player;

import org.edu.ntnu.idatt2003.group49.millions.model.calculator.ChangeCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.SaleAllocation;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

public class Portfolio {
  static Logger logger = Logger.getLogger(Portfolio.class.getName());

  private final List<Share> shares;
  private final Map<Integer, BigDecimal> valueMap;

  public Portfolio() {
    shares = new ArrayList<>();
    valueMap = new HashMap<>();
  }

  public boolean addShare(Share share) {
    Objects.requireNonNull(share, "'share' cannot be null");

    return shares.add(share);
  }

  public boolean removeShare(Share share) {
    return shares.remove(
        Objects.requireNonNull(share, "'share' cannot be null")
    );
  }

  public List<Share> getShares() {
    return shares;
  }

  public List<Share> getShares(String symbol) {
    Objects.requireNonNull(symbol, "'symbol' cannot be null");
    return shares.stream()
        .filter(s -> Objects.equals(s.getStock().getSymbol(), symbol))
        .toList();
  }

  public boolean contains(Share share) {
    return shares.contains(
      Objects.requireNonNull(share, "'share' cannot be null")
    );
  }

  public void addValue(int week, BigDecimal value) {
    System.out.println("[week=" + week + ", value=" + value + "]");
    valueMap.put(week, value);
  }

  public Map<Integer, BigDecimal> getValueMap() {
    return valueMap;
  }

  public BigDecimal getValue() {
    return shares.stream()
            .map(share -> new SaleCalculator(share).calculateGross())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**
   * returns the highest price of the portfolio
   *
   * @return the highest value
   */
  public BigDecimal getHighestValue() {
    return valueMap.values().stream().max(BigDecimal::compareTo)
      .orElse(BigDecimal.ZERO);
  }

  /**
   * returns the lowest value of the portfolio
   *
   * @return the lowest value
   */
  public BigDecimal getLowestValue() {
    return valueMap.values().stream().min(BigDecimal::compareTo)
      .orElse(BigDecimal.ZERO);
  }

  // TODO: make test for this
  public BigDecimal getChangeSinceFirstPurchase() {
    if (shares.isEmpty()) {
      logger.warning("No shares in Portfolio");
      return BigDecimal.ZERO;
    }

    List<BigDecimal> values = valueMap
      .values()
      .stream()
      .filter(v -> (v.compareTo(BigDecimal.ZERO) != 0))
      .toList();

    if (values.isEmpty()) {
      return BigDecimal.ZERO;
    }

    return ChangeCalculator.calculatePercentageChange(
      values.getFirst(),
      values.getLast()
    );
  }

  public void reduceShare(Share share, BigDecimal quantityToSell) {
    Objects.requireNonNull(share, "share cannot be null");
    Objects.requireNonNull(quantityToSell, "quantityToSell cannot be null");

    if (quantityToSell.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("quantityToSell must be greater than zero");
    }

    int shareIndex = shares.indexOf(share);

    if (shareIndex == -1) {
      throw new IllegalStateException("Cannot reduce a share that is not owned");
    }

    if (quantityToSell.compareTo(share.getQuantity()) > 0) {
      throw new IllegalArgumentException("Cannot sell more shares than owned");
    }

    BigDecimal remainingQuantity = share.getQuantity().subtract(quantityToSell);

    if (remainingQuantity.compareTo(BigDecimal.ZERO) == 0) {
      shares.remove(shareIndex);
      return;
    }

    Share remainingShare = new Share(
            share.getStock(),
            remainingQuantity,
            share.getPurchasePrice()
    );

    shares.set(shareIndex, remainingShare);
  }

  public List<SaleAllocation> planSale(String symbol, BigDecimal quantityToSell) {
    Objects.requireNonNull(symbol, "symbol cannot be null");
    Objects.requireNonNull(quantityToSell, "quantityToSell cannot be null");

    if (quantityToSell.compareTo(BigDecimal.ZERO) <= 0){
      throw new IllegalArgumentException("quantityToSell must be greater than zero");
    }

    BigDecimal quantity = quantityToSell;
    List<SaleAllocation> allocations = new ArrayList<>();

    for (Share share: getShares(symbol)) {
      BigDecimal quantityFromThisShare = share.getQuantity().min(quantity);

      SaleAllocation allocation = new SaleAllocation(share, quantityFromThisShare);
      allocations.add(allocation);

      quantity = quantity.subtract(quantityFromThisShare);

      if (quantity.compareTo(BigDecimal.ZERO) == 0) {
        return allocations;
      }
    }
    throw new IllegalArgumentException("Cannot sell more shares than owned");
  }

  public void applySale(List<SaleAllocation> allocations) {
    Objects.requireNonNull(allocations, "allocations cannot be null");

    if (allocations.isEmpty()) {
      throw new IllegalArgumentException("List cannot be empty");
    }

    for (SaleAllocation allocation : allocations) {
      Objects.requireNonNull(allocation, "allocation cannot be null");

      if (!contains(allocation.getShare())) {
        throw new IllegalArgumentException("Cannot sell a share that is not owned");
      }
    }

    for (SaleAllocation allocation : allocations) {
      reduceShare(allocation.getShare(), allocation.getQuantity());
    }
  }
}
