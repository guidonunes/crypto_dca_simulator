package br.com.dcasimulator.runner;

import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.service.CsvParser;
import br.com.dcasimulator.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {

    private final CsvParser csvParser;

    @Autowired
    public ConsoleApp(CsvParser csvParser) {
        this.csvParser = csvParser;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- 🟢 STARTING CSV IMPORT TEST ---");

        // 1. Call the parser with your specific filename from 'resources'
        // Make sure this matches the filename in your screenshot exactly!
        String fileName = "btc_brl_data.csv";

        List<PriceRecord> records = csvParser.loadDataFromCsv(fileName);


        if (records.isEmpty()) {
            System.out.println("⚠️ No records found! Check the filename.");
        } else {
            System.out.println("✅ Successfully loaded " + records.size() + " records.");
            System.out.println("--- First 5 Rows ---");
            records.stream().limit(5).forEach(record -> {
                System.out.println(record);
            });
        }

        System.out.println("--- 🔴 END TEST ---");
    }


}