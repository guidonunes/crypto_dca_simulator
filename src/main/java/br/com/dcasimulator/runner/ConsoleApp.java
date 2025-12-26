package br.com.dcasimulator.runner;

import br.com.dcasimulator.entity.Asset;
import br.com.dcasimulator.entity.Price;
import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.repository.AssetRepository;
import br.com.dcasimulator.repository.PriceRepository;
import br.com.dcasimulator.service.CsvParser;
import br.com.dcasimulator.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@Order(2)
public class ConsoleApp implements CommandLineRunner {

    private final CsvParser csvParser;
    private final PriceRepository priceRepository;
    private final AssetRepository assetRepository;

    @Autowired
    public ConsoleApp(CsvParser csvParser,  PriceRepository priceRepository, AssetRepository assetRepository) {
        this.csvParser = csvParser;
        this.priceRepository = priceRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- 🟢 STARTING MULTI-ASSET IMPORT ---");

        List<Asset> allAssets = assetRepository.findAll();

        if (allAssets.isEmpty()) {
            System.out.println("⚠️ No assets found! check @Order annotation.");
            return;
        }

        for (Asset asset : allAssets) {
            String symbol = asset.getSymbol();

            // Construct filename: "BTC" -> "btc_brl_data.csv"
            String fileName = symbol.toLowerCase() + "_brl_data.csv";

            System.out.println("Processing Asset: " + symbol + " (File: " + fileName + ")");

            try {
                // Try to load the file
                List<PriceRecord> rawRecords = csvParser.loadDataFromCsv(fileName);

                if (rawRecords.isEmpty()) {
                    System.out.println("   ⚠️ File empty or not found (CsvParser returned empty list).");
                    continue; // Skip to next asset
                }

                // Convert to Entities
                List<Price> entitiesToSave = new ArrayList<>();
                for (PriceRecord record : rawRecords) {
                    entitiesToSave.add(new Price(
                            symbol,             // Use dynamic symbol!
                            record.getDate(),
                            record.getClose()
                    ));
                }

                // Save
                priceRepository.saveAll(entitiesToSave);
                System.out.println("   ✅ Saved " + entitiesToSave.size() + " rows for " + symbol);

            } catch (Exception e) {
                // If file is missing (e.g. for XRP), just log and continue
                System.out.println("   ❌ Could not load file for " + symbol + ". Skipping...");
            }
        }

        System.out.println("--- 🔴 IMPORT FINISHED ---");
    }
}