package org.edu.ntnu.idatt2003.group49.millions;

import java.util.ArrayList;

public class Portfolio {
  private List<Share> shares;

  public Portfolio() {
    shares = new ArrayList<>();
  }

  public boolean addShare(Share share) {
    shares.add(share);
  }

  public boolean removeShare(Share share) {
    shares.remove(share);
  }

  public List<Share> getShares() {
    return shares;
  }

  public List<Share> getShares(String symbol) {

  }

  public boolean contains(Share share) {
    return
  }
}
