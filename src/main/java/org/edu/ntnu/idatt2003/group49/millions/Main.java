package org.edu.ntnu.idatt2003.group49.millions;

import org.edu.ntnu.idatt2003.group49.millions.transaction.Purchase;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello Millions!");
    TransactionArchive transactionArchive = new TransactionArchive();
    transactionArchive.countDistinctWeeks();
  }
}
