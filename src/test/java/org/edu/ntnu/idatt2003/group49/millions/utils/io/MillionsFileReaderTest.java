package org.edu.ntnu.idatt2003.group49.millions.utils.io;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MillionsFileReaderTest {

  @Test
  void readCSVFile_throwsWhenPathIsNull() {
    assertThrows(NullPointerException.class, () -> MillionsFileReader.readCSVFile(null));
  }

  @Test
  void convertCSVFileToStocksList_readsAllStocksFromFile() {
    List<Stock> data = MillionsFileReader.convertCSVFileToStocksList(Path.of("src/main/resources/sp500.csv"));
    assertEquals(503, data.size());
  }
}