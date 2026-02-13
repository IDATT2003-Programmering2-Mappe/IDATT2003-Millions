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
}
