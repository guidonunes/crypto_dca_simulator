package br.com.dcasimulator.runner;

import br.com.dcasimulator.entity.Price;
import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.entity.SimulationResult;
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

    @Autowired
    public ConsoleApp(CsvParser csvParser,  PriceRepository priceRepository) {
        this.csvParser = csvParser;
        this.priceRepository = priceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- 🟢 STARTING CSV IMPORT TEST ---");

        String fileName = "btc_brl_data.csv";

        List<PriceRecord> records = csvParser.loadDataFromCsv(fileName);


        if (records.isEmpty()) {
            System.out.println("⚠️ No records found! Check the filename.");
            return;
        }

        System.out.println(" --> Parsed " + records.size() + " price records!");

        List<Price> entitiesToSave = new ArrayList<>();

        for(PriceRecord record : records) {
            Price priceEntity = new Price(
                    "BTC",
                    record.getDate(),
                    record.getClose()
            );
            entitiesToSave.add(priceEntity);
        }

        priceRepository.saveAll(entitiesToSave);

        System.out.println("✅ SUCCESSFULLY SAVED " + entitiesToSave.size() + " ROWS TO DB!");
        System.out.println("--- 🔴 IMPORT FINISHED ---");
    }



}