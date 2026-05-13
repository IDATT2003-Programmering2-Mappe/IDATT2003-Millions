package org.edu.ntnu.idatt2003.group49.millions.model.player;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Share;
import org.edu.ntnu.idatt2003.group49.millions.model.calculator.SaleCalculator;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.SaleAllocation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Portfolio {
  private final List<Share> shares;

  public Portfolio() {
    shares = new ArrayList<>();
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

  public BigDecimal getValue() {
    return shares.stream()
            .map(share -> new SaleCalculator(share).calculateGross())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
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
