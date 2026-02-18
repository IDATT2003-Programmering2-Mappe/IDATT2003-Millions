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
    this.prices.add(Objects.requireNonNull(salesPrice, "salesPrice cannot be null"));
    if (symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be blank");
    }
    if (company.isBlank()) {
      throw new IllegalArgumentException("Company cannot be blank");
    }
  }

  @Override
  public String toString() {
    return "Stock [symbol=" + symbol + ", company=" + company + ", prices=" + getSalesPrice() + "]";
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
    Objects.requireNonNull(price, "price cannot be null");
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    }
    prices.add(price);
  }
}
