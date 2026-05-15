package org.edu.ntnu.idatt2003.group49.millions.config;

import java.nio.file.Path;
import java.util.Map;

public class PreDefinedExchanges {
  public PreDefinedExchanges() {}

    public static final Map<String, Path> FILES = Map.of(
            "S&P 500", Path.of("src/main/resources/sp500.csv"),
            "Nasdaq with sectors", Path.of("src/main/resources/NasdaqWithSectors.csv")
    );
}
