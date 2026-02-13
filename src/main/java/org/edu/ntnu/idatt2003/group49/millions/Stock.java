package org.edu.ntnu.idatt2003.group49.millions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stock {
  private final String symbol;
  private final String company;
  private final List<BigDecimal> prices;

  public Stock(String symbol, String company, BigDecimal salesPrice) {
    this.symbol = Objects.requireNonNull(symbol, "symbol cannot be null");
    this.company = Objects.requireNonNull(company, "company cannot be null");

    this.prices = new ArrayList<>();
    this.prices.add(salesPrice);
  }

  public String getSymbol() {
    return symbol;
  }

  public String getCompany() {
    return company;
  }

  public BigDecimal getSalesPrice() {
    return prices.getLast();
  }

  public void addNewSalesPrice (BigDecimal price) {
    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      prices.add(price);
    }

  }
}
