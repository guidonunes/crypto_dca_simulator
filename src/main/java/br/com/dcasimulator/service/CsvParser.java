package br.com.dcasimulator.service;

import br.com.dcasimulator.model.PriceRecord;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class CsvParser {
    public List<PriceRecord> loadDataFromCsv(String fileName) {
        List<PriceRecord> records = new ArrayList<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "Br"));

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))
                ))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Clean quotes from strings: "16.221,00" -> 16.221,00
                String dateStr = values[0].replace("\"", "");
                String closeStr = values[1].replace("\"", "");
                String openStr = values[2].replace("\"", "");
                String highStr = values[3].replace("\"", "");
                String lowStr = values[4].replace("\"", "");

                // Parse
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                BigDecimal close = new BigDecimal(numberFormat.parse(closeStr).toString());
                BigDecimal open = new BigDecimal(numberFormat.parse(openStr).toString());
                BigDecimal high = new BigDecimal(numberFormat.parse(highStr).toString());
                BigDecimal low = new BigDecimal(numberFormat.parse(lowStr).toString());

                records.add(new PriceRecord(date, close, open, high, low));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
}
