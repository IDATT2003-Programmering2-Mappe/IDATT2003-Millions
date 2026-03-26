package org.edu.ntnu.idatt2003.group49.millions.model.io;

import org.edu.ntnu.idatt2003.group49.millions.model.Stock;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MillionsFileReaderTest {

  @Test
  void readCSVFile_throwsWhenPathIsNull() {
    assertThrows(NullPointerException.class, () -> MillionsFileReader.readCSVFile(null));
  }

  @Test
  void readCSVFile_returnsCorrectData() {
    List<String[]> data = MillionsFileReader.readCSVFile(Path.of("src/main/resources/player_networth.csv"));
    for(String[] line : data) {
      System.out.println(Arrays.toString(line));
    }

    System.out.println(Arrays.toString(data.getFirst()));
  }

  @Test
  void convertCSVFileToStocksList_readsAllStocksFromFile() {
    List<Stock> data = MillionsFileReader.convertCSVFileToStocks(Path.of("src/main/resources/sp500.csv"));
    data.forEach(System.out::println);
    assertEquals(503, data.size());
  }
}