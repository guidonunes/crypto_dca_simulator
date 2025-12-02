package br.com.dca;
import br.com.dca.dao.AssetPriceDAO;
import br.com.dca.factory.ConnectionFactory;
import br.com.dca.model.PriceRecord;
import br.com.dca.strategy.DcaStrategy;
import br.com.dca.util.CsvParser;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Database connection test
//        try {
//            Connection connection = ConnectionFactory.getConnection();
//            System.out.println("Successfully connected.");
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
        //CsvParser test
//        CsvParser csvParser = new CsvParser();
//        String btcPath = "src/main/resources/btc_brl_history.csv";
//
//        System.out.println("---1. Testing the CSV Parser");
//        List<PriceRecord> btcData = csvParser.parse(btcPath, 1);
//
//        if (btcData.isEmpty()) {
//            System.out.println("---1. No data found");
//            return;
//        }
//
//        System.out.println("Successfuly parsed " + btcData.size() + " data");
//
//        System.out.println("\n First 5 Records --- Verify if it is right");
//        for (int i = 0; i < Math.min(5, btcData.size()); i++) {
//            PriceRecord record = btcData.get(i);
//
//            System.out.println("Date: " + record.getDate() + " | Price: R$ " + record.getClosePrice());
//        }

        //DAO Test
        long startTime = System.currentTimeMillis();

        try {
            CsvParser csvParser = new CsvParser();
            AssetPriceDAO assetPriceDAO = new AssetPriceDAO();

            System.out.println("-- Processing Bitcoin Data --");
            List<PriceRecord> btcData = csvParser.parse("src/main/resources/btc_brl_history.csv", 1);

//            if (!btcData.isEmpty()) {
//                assetPriceDAO.bulkInsertPrices(btcData);
//            } else {
//                System.err.println("No Bitcoin data found!");
//            }

            if (!btcData.isEmpty()) {
                DcaStrategy dca = new DcaStrategy();

                // Simulate buying R$ 100.00 of Bitcoin every 30 days
                BigDecimal result = dca.calculate(btcData, new BigDecimal("100.00"));
            }


            System.out.println("\n-- Processing Ethereum Data");
            List<PriceRecord> ethData = csvParser.parse("src/main/resources/eth_brl_history.csv", 2);
//
//            if (!ethData.isEmpty()) {
//                assetPriceDAO.bulkInsertPrices(ethData);
//            } else {
//                System.err.println("No Ethereum data found!");
//
//            }
            if (!ethData.isEmpty()) {
                DcaStrategy dca = new DcaStrategy();

                // Simulate buying R$ 100.00 of Bitcoin every 30 days
                BigDecimal result = dca.calculate(ethData, new BigDecimal("100.00"));
            }
        } catch (SQLException ex) {
            System.err.println("Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");

    }
}
