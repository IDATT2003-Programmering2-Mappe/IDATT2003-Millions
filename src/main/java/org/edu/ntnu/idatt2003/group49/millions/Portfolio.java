package org.edu.ntnu.idatt2003.group49.millions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Portfolio {
  private final List<Share> shares;

  public Portfolio() {
    shares = new ArrayList<>();
  }

  public boolean addShare(Share share) {
    Objects.requireNonNull(share, "share cannot be null");
    return shares.add(share);
  }

  public boolean removeShare(Share share) {
    Objects.requireNonNull(share, "share cannot be null");
    return shares.remove(share);
  }

  public List<Share> getShares() {
    return shares;
  }

  public List<Share> getShares(String symbol) {
    Objects.requireNonNull(symbol, "symbol cannot be null");
    return shares.stream()
        .filter(s -> Objects.equals(s.getStock().getSymbol(), symbol))
        .toList();
  }

  public boolean contains(Share share) {
    Objects.requireNonNull(share, "share cannot be null");
    return shares.contains(share);
  }
}
