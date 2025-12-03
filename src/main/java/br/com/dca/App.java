package br.com.dca;
import br.com.dca.dao.AssetPriceDAO;
import br.com.dca.factory.ConnectionFactory;
import br.com.dca.factory.StrategyFactory;
import br.com.dca.model.PriceRecord;
import br.com.dca.strategy.DcaStrategy;
import br.com.dca.strategy.InvestmentStrategy;
import br.com.dca.strategy.LumpSumStrategy;
import br.com.dca.util.CsvParser;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


/**
 * Database connection test
 *       try {
 *             Connection connection = ConnectionFactory.getConnection();
 *             System.out.println("Successfully connected.");
 *         } catch (SQLException e) {
 *             System.err.println(e.getMessage());
 *         }
 *         //CsvParser test
 *         CsvParser csvParser = new CsvParser();
 *         String btcPath = "src/main/resources/btc_brl_history.csv";
 *
 *         System.out.println("---1. Testing the CSV Parser");
 *         List<PriceRecord> btcData = csvParser.parse(btcPath, 1);
 *
 *         if (btcData.isEmpty()) {
 *          System.out.println("---1. No data found");
 *             return;
 *         }
 *
 *         System.out.println("Successfuly parsed " + btcData.size() + " data");
 *
 *         System.out.println("\n First 5 Records --- Verify if it is right");
 *         for (int i = 0; i < Math.min(5, btcData.size()); i++) {
 *             PriceRecord record = btcData.get(i);
 *
 *             System.out.println("Date: " + record.getDate() + " | Price: R$ " + record.getClosePrice());
 *         }
 *
 * Inserting CSV Data into the DB
 * Bitcoin :
 *            if (!btcData.isEmpty()) {
 *                 assetPriceDAO.bulkInsertPrices(btcData);
 *             } else {
 *                 System.err.println("No Bitcoin data found!");
 *             }
 * Ethereum :
 *
 *
 *             if (!ethData.isEmpty()) {
 *                 assetPriceDAO.bulkInsertPrices(ethData);
 *             } else {
 *                 System.err.println("No Ethereum data found!");
 *
 *             }
 *
 *  // DAO Test Hard coded
 *         long startTime = System.currentTimeMillis();
 *
 *         try {
 *             CsvParser csvParser = new CsvParser();
 *             AssetPriceDAO assetPriceDAO = new AssetPriceDAO();
 *
 *             System.out.println("-- Processing Bitcoin Data --");
 *             List<PriceRecord> btcData = csvParser.parse("src/main/resources/btc_brl_history.csv", 1);
 *
 *             if (!btcData.isEmpty()) {
 *                 DcaStrategy dca = new DcaStrategy();
 *                 LumpSumStrategy lumpSum = new LumpSumStrategy();
 *
 *                 // Simulate buying R$ 100.00 of Bitcoin every 30 days
 *                 dca.calculate(btcData, new BigDecimal("100") );
 *                 lumpSum.calculate(btcData, new BigDecimal("7200"));
 *             }
 *
 *             System.out.println("\n-- Processing Ethereum Data");
 *             List<PriceRecord> ethData = csvParser.parse("src/main/resources/eth_brl_history.csv", 2);
 *
 *             if (!ethData.isEmpty()) {
 *                 DcaStrategy dca = new DcaStrategy();
 *                 LumpSumStrategy lumpSum = new LumpSumStrategy();
 *
 *                 // Simulate buying R$ 100.00 of Bitcoin every 30 days
 *                 dca.calculate(ethData, new BigDecimal("100.00"));
 *                 lumpSum.calculate(ethData, new BigDecimal("7200"));
 *
 *             }
 *         } catch (SQLException ex) {
 *             System.err.println("Database error: " + ex.getMessage());
 *             ex.printStackTrace();
 *         }
 *         long endTime = System.currentTimeMillis();
 *         System.out.println("Total execution time: " + (endTime - startTime) + " ms");
 *
 *
 */

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CsvParser parser = new CsvParser();

        System.out.println("Welcome to the DCA Simulator!");

        int userChoice = -1;

        try {
            do {
                System.out.println("""
                        
                        Choose an option:\
                        
                        1 - See available assets \
                        
                        2 - Choose an investment strategy to simulate \
                        
                        0 - Quit""");
                System.out.print("Select an option: ");

                if(scanner.hasNextInt()) {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.next();
                    userChoice = -1;
                }

                switch (userChoice) {
                    case 1:
                        System.out.println("\n---Available assets ---");
                        System.out.println("1. Bitcoin (BTC)");
                        System.out.println("2. Ethereum (ETH)");
                        break;
                    case 2:

                }


            } while (userChoice != 0);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void runSimulation(Scanner scanner, CsvParser parser) {
        System.out.println("\n-- New Simulation ---");
        System.out.println("Enter Strategy (DCA or LUMP)");
        String strategyType = scanner.nextLine();

        System.out.println("Enter Investment Amount (e.g. 100): ");
        String investmentAmount = scanner.nextLine();
        BigDecimal amount;
        try {
            amount = new BigDecimal(investmentAmount);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid Investment Amount");
            return;
        }

        InvestmentStrategy strategy;
        try {
            strategy = StrategyFactory.getStrategy(strategyType);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid Strategy: " + ex.getMessage());
            return;
        }


        System.out.println("\n-- Processing Bitcoin Data --");
        List<PriceRecord> btcData = parser.parse("src/main/resources/btc_brl_history.csv", 1);
        if (!btcData.isEmpty()) {
            strategy.calculate(btcData, amount);
        }

        System.out.println("\n-- Processing Ethereum Data");
        List<PriceRecord> ethData = parser.parse("src/main/resources/eth_brl_history.csv", 2);

        if (!ethData.isEmpty()) {
            strategy.calculate(ethData, amount);
        }
    }
}
