package org.edu.ntnu.idatt2003.group49.millions.model.io;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

class MillionsFileWriterTest {

  private Map<String, Stock> stockData;

  @BeforeEach
  void setUp() {
    stockData = new HashMap<>();
    stockData.put("APPL", new Stock("APPL", "Apple", new BigDecimal("2000")));
    System.out.println();
  }

  @Test
  void fileWriter_WritesCorrectDataToFile() {
    MillionsFileWriter.writeStockDataToFile(Path.of("src/main/resources/test.csv"), this.stockData);
  }
}