package org.edu.ntnu.idatt2003.group49.millions.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Represents a stock.
 * A stock has a unique symbol, company name and price history.
 * The most recent price in history is the current sales price
 */
public class Stock {
  private final String symbol;
  private final String company;
  private final List<BigDecimal> prices;

  /**
   * Constructs a Stock object with a specified symbol, company name,
   * and initial sales price.
   *
   * @param symbol      the unique symbol representing the stock (cannot be null or blank)
   * @param company     the company name (cannot be null or blank)
   * @param salesPrice  the initial sales price (cannot be null)
   * @throws NullPointerException     if any of the parameters are null
   * @throws IllegalArgumentException if symbol or company is blank
   */
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
  /**
   * Returns a string representation of the stock.
   *
   * @return a string containing the symbol, company name and current sales price
   */
  @Override
  public String toString() {
    return "Stock [symbol=" + symbol + ", company=" + company + ", prices=" + getSalesPrice() + "]";
  }

  /**
   * Returns the Stock symbol
   *
   * @return the stock symbol
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Returns the company name
   *
   * @return the name of the company
   */
  public String getCompany() {
    return company;
  }

  /**
   * Returns the most recent sales price of the stock.
   *
   * @return the current sales price of the stock
   */

  public BigDecimal getSalesPrice() {
    return prices.getLast();
  }

  /**
   * Adds a new sales price to the price history.
   *
   * @param price the new sales price (must not be null or negative)
   * @throws NullPointerException if price is null
   * @throws IllegalArgumentException if price is negative
   */
  public void addNewSalesPrice (BigDecimal price) {
    Objects.requireNonNull(price, "price cannot be null");
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    }
    prices.add(price);
  }

  /**
   * Returns all earlier prices for the stock
   *
   * @return an immutable list of historical prices
   */
  public List<BigDecimal> getHistoricalPrices() {
    return List.copyOf(prices);
  }

  /**
   * returns the highest price for this stock
   *
   * @return the highest price
   */
  public BigDecimal getHighestPrice() {
    return prices.stream().max(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);
  }

  /**
   * returns the lowest price for this stock
   *
   * @return the lowest price
   */
  public BigDecimal getLowestPrice() {
    return prices.stream().min(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);
  }


  /**
   * returns the price change since last price update
   *
   * @return pricechange
   */
  public BigDecimal getLatestPriceChange() {

    if (prices.size() < 2) {
      return BigDecimal.ZERO;
    }

    BigDecimal latestPrice = getSalesPrice();
    BigDecimal previousPrice = prices.get(prices.size() - 2);

    return latestPrice.subtract(previousPrice);
  }
}
