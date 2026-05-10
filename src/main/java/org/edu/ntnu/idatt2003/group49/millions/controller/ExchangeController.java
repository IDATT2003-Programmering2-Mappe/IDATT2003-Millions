package org.edu.ntnu.idatt2003.group49.millions.controller;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Exchange;

public class ExchangeController {
  private final Exchange exchange;

  public ExchangeController(Exchange exchange) {
    this.exchange = exchange;
  }

  public void advance() {
    System.out.println("Advance clicked");
    exchange.advance();
  }
}
