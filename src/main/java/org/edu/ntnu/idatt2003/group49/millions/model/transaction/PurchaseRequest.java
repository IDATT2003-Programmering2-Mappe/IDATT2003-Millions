package org.edu.ntnu.idatt2003.group49.millions.model.transaction;

import org.edu.ntnu.idatt2003.group49.millions.model.player.Player;

import java.math.BigDecimal;

public record PurchaseRequest(String symbol, BigDecimal quantity, Player player) {}
