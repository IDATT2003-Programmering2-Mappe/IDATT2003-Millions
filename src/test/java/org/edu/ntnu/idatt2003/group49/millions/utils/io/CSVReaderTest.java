package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.exchange.Stock;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

  @Test
  void readCSVFile_throwsWhenPathIsNull() {
    assertThrows(NullPointerException.class, () -> CSVReader.readCSV(null));
  }

  @Test
  void convertCSVFileToStocksList_readsAllStocksFrom() {
    List<Stock> data = CSVReader.convertCSVToStocksList(Path.of("src/main/resources/sp500.csv"));
    assertEquals(503, data.size());
  }
}