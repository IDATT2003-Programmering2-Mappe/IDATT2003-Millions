package org.edu.ntnu.idatt2003.group49.millions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionArchiveTest {

  private TransactionArchive transactionArchive;

  @BeforeEach
  void setUp() {
    transactionArchive = new TransactionArchive();
  }

  @Test
  void add() {
  }

  @Test
  void isEmpty() {
  }

  @Test
  void getTransactions() {
  }

  @Test
  void getPurchases() {
  }

  @Test
  void getSales() {
  }

  @Test
  void countDistinctWeeks() {
    transactionArchive.countDistinctWeeks();
  }
}