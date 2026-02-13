package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.transaction.Purchase;
import org.edu.ntnu.idatt2003.group49.millions.transaction.Sale;
import org.edu.ntnu.idatt2003.group49.millions.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionArchive {
  private List<Transaction> transactions;

  public TransactionArchive() {
    transactions = new ArrayList<Transaction>();
  }

  public boolean add(Transaction transaction) {
    Objects.requireNonNull(transaction, "transaction cannot be null");
    return transactions.add(transaction);
  }

  public boolean isEmpty() {
    return transactions.isEmpty();
  }

  public List<Transaction> getTransactions(int week) {
    if (week < 0) {
      throw new IllegalArgumentException("week number cannot be negative");
    }
    if (week > transactions.size()) {
      throw new IllegalArgumentException("week number is greater than size of the transaction array");
    }
    return transactions.stream()
        .filter(t -> t.getWeek() == week)
        .toList();
  }

  // TODO:
  public List<Purchase> getPurchases(int week) {
    if (week < 0) {
      throw new IllegalArgumentException("week number cannot be negative");
    }
    if (week > transactions.size()) {
      throw new IllegalArgumentException("week number is greater than size of the transaction array");
    }
    return null;
  }

  // TODO:
  public List<Sale> getSales(int week) {
    if (week < 0) {
      throw new IllegalArgumentException("week number cannot be negative");
    }
    if (week > transactions.size()) {
      throw new IllegalArgumentException("week number is greater than size of the transaction array");
    }
    return null;
  }

  public int countDistinctWeeks() {
    return transactions.stream()
        .map(Transaction::getWeek)
        .distinct()
        .toList()
        .size();
  }
}
