package org.edu.ntnu.idatt2003.group49.millions.model.player;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.SaleAllocation;
import org.edu.ntnu.idatt2003.group49.millions.utils.io.CSVReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.logging.Logger;

public class Portfolio {
  static Logger logger = Logger.getLogger(CSVReader.class.getName());

  private final List<Share> shares;
  private final Map<Integer, BigDecimal> valueMap;

  public Portfolio() {
    shares = new ArrayList<>();
    valueMap = new HashMap<>();
  }

  public boolean addShare(Share share) {
    return shares.add(
        Objects.requireNonNull(share, "'share' cannot be null")
    );
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
  public BigDecimal getCurrentChange() {
    if (valueMap.isEmpty()) {
      logger.warning("No values in Portfolio");
      return BigDecimal.ZERO;
    }

    return calculatePriceChange(
      valueMap.values().stream().toList().getFirst(),
      valueMap.values().stream().toList().getLast()
    );
  }

  // TODO: make tests for this
  public BigDecimal calculatePriceChange(BigDecimal startPrice, BigDecimal currentPrice) {
    return (currentPrice.subtract(startPrice))
      .divide(startPrice, 4, RoundingMode.HALF_UP)
      .multiply(new BigDecimal("100"))
      .setScale(2, RoundingMode.HALF_UP);
  }

  public void reduceShare(Share share, BigDecimal quantityToSell) {
    Objects.requireNonNull(share, "share cnnot be null");
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
