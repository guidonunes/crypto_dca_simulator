package br.com.dca.util;

import br.com.dca.model.PriceRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    public List<PriceRecord> parse(String filePath, int assetId) {
        List<PriceRecord> records = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length < 2) continue;

                try {
                    String rawDate = values[0].replace("\"", "");
                    String rawPrice = values[1].replace("\"", "");

                    if (rawPrice == null || rawPrice.trim().isEmpty()) {
                        continue; // Skip rows with empty price
                    }

                    LocalDate date = LocalDate.parse(rawDate, formatter);
                    rawPrice = rawPrice.replace(".", "").replace(",", ".");

                    BigDecimal price = new BigDecimal(rawPrice);
                    
                    // Only add records with valid positive prices
                    if (price.compareTo(BigDecimal.ZERO) > 0) {
                        records.add(new PriceRecord(assetId, date, price));
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid row: " + line + " -> " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
