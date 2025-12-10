package br.com.dca;
import br.com.dca.dao.AssetPriceDAO;
import br.com.dca.dao.SimulationDAO;
import br.com.dca.factory.ConnectionFactory;
import br.com.dca.factory.StrategyFactory;
import br.com.dca.model.PriceRecord;
import br.com.dca.model.SimulationResult;
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
     // Insert data

     CsvParser csvParser = new CsvParser();
     AssetPriceDAO assetPriceDAO = new AssetPriceDAO();
     String assetPath = "src/main/resources/nasd11_brl_history.csv";
     List<PriceRecord> assetData = csvParser.parse(assetPath, 8);

     try {
     if (!assetData.isEmpty()) {
     assetPriceDAO.bulkInsertPrices(assetData);
     } else {
     System.err.println("No data found!");
     }
     } catch (Exception e) {
     System.out.println(e.getMessage());
     }

 *
 */

public class App {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        CsvParser parser = new CsvParser();

        System.out.println(">> WELCOME TO DCA SIMULATOR <<");
        System.out.println("Simulate strategy using historical data from 2020 to 2025");

        int userChoice = -1;

        try {
            do {
                System.out.println("""

                        Choose an option:\

                        1 - See available assets \

                        2 - Simulate a strategy \
                        
                        3 - Simulation history \
                        
                        4 - Delete simulation \

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
                        System.out.println(">>CRYPTO<<");
                        System.out.println();
                        System.out.println("1. Bitcoin (BTC)");
                        System.out.println("2. Ethereum (ETH)");
                        System.out.println("3. Ripple (XRP)");
                        System.out.println("4. Binance Chain (BNB)");
                        System.out.println("5. Solana (SOL)");
                        System.out.println();
                        System.out.println(">>STOCK INDEXES<<");
                        System.out.println();
                        System.out.println("6. B3 Brazil (BOVA11)");
                        System.out.println("7. S&P500 Index (IVVB11)");
                        System.out.println("8. NASDAQ100 Index (NASD11)");
                        break;
                    case 2:
                        runSimulation(scanner, parser);
                        break;
                    case 3:
                        showHistory();
                        break;
                    case 4:
                        deleteMenu(scanner);
                        break;
                    case 0:
                        System.out.println("Bye bye!");
                        break;
                    default:
                        System.out.printf("Please select a valid option!\n");

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
        // 1. Choose Strategy
        System.out.println("Enter Strategy (DCA or LUMP)");
        String strategyType = scanner.nextLine();
        // 2. Choose Asset
        System.out.println("Choose asset:");
        System.out.println(">>CRYPTO<<");
        System.out.println();
        System.out.println("1. Bitcoin (BTC)");
        System.out.println("2. Ethereum (ETH)");
        System.out.println("3. Ripple (XRP)");
        System.out.println("4. Binance Chain (BNB)");
        System.out.println("5. Solana (SOL)");
        System.out.println();
        System.out.println(">>STOCK INDEXES<<");
        System.out.println();
        System.out.println("6. B3 Brazil (BOVA11)");
        System.out.println("7. S&P500 Index (IVVB11)");
        System.out.println("8. NASDAQ100 Index (NASD11)");
        System.out.println();
        System.out.print("Select an option (from 1 to 8): ");

        int assetChoice = -1;
        if(scanner.hasNextInt()) {
            assetChoice = scanner.nextInt();
            scanner.nextLine();
        } else {
            scanner.nextLine();
        }
        // 3. Call the helper method
        String filePath = getFilePathForAsset(assetChoice);

        if(filePath == null) {
            System.out.println("Error: Asset not found!");
            return;
        }

        // 4. Get Amount
        System.out.println("Enter Investment Amount (e.g. 100): ");
        String investmentAmount = scanner.nextLine();
        BigDecimal amount = new BigDecimal(investmentAmount);

        // 5. Run the specific simulation
        InvestmentStrategy strategy = StrategyFactory.getStrategy(strategyType);

        System.out.println("Processing data...");

        List<PriceRecord> data = parser.parse(filePath, assetChoice);

        if(!data.isEmpty()) {
            SimulationResult result = strategy.calculate(data, amount);
            String assetName = switch(assetChoice) {
                case 1 -> "Bitcoin";
                case 2 -> "Ethereum";
                case 3 -> "Ripple";
                case 4 -> "Binance Chain";
                case 5 -> "Solana";
                case 6 -> "B3 Index";
                case 7 -> "S&P500 Index";
                case 8 -> "NASDAQ100 Index";
                default -> null;
            };

            printReport(assetName, result);
            new SimulationDAO().save(result, assetName);
        }
    }


    private static void printReport(String assetName, SimulationResult result) {
        System.out.println("-------------------------------------");
        System.out.println(" Results for " + assetName);
        System.out.println("-------------------------------------");
        System.out.println("Strategy:       " + result.getStrategyName());
        System.out.println("Total Invested: R$ " + result.getInitialInvestment());
        System.out.println("Final Value:    R$ " + result.getFinalValue());
        System.out.println("Profit/Loss:    R$ " + result.getProfit());
        System.out.println("Percent Gain:   " + result.getPercentGain() + "%");
        System.out.println("-------------------------------------\n");
    }

    private static String getFilePathForAsset(int assetChoice) {
        return switch (assetChoice) {
            case 1 -> "src/main/resources/btc_brl_history.csv";
            case 2 -> "src/main/resources/eth_brl_history.csv";
            case 3 -> "src/main/resources/xrp_brl_history.csv";
            case 4 -> "src/main/resources/bnb_brl_history.csv";
            case 5 -> "src/main/resources/sol_brl_history.csv";
            case 6 -> "src/main/resources/bova11_brl_history.csv";
            case 7 -> "src/main/resources/ivvb11_brl_history.csv";
            case 8 -> "src/main/resources/nasd11_brl_history.csv";
            default -> null;
        };
    }


    private static void showHistory() {
        System.out.println("\n--- Simulation History ---");

        // 1. Get data from DB
        SimulationDAO dao = new SimulationDAO();
        List<SimulationResult> history = dao.findAll();

        if (history.isEmpty()) {
            System.out.println("No history found yet. Run a simulation first!");
            return;
        }

        System.out.printf("%-5s %-10s %-15s %-15s %-15s %-10s%n",
               "ID", "Strategy", "Asset", "Invested", "Profit", "Gain %");
        System.out.println("-----------------------------------------------------------------------");

        // 3. Print Rows
        for (SimulationResult r : history) {
            System.out.printf("%-5d %-10s %-15s R$ %-12s R$ %-12s %s%%%n",
                    r.getId(),
                    r.getStrategyName(),
                    r.getAssetName(),
                    r.getInitialInvestment(),
                    r.getProfit(),
                    r.getPercentGain()
            );
        }
        System.out.println("-----------------------------------------------------------------------\n");
    }

    private static void deleteMenu(Scanner scanner) {
        System.out.println("\n--- Delete Options ---");
        System.out.println("1 - Delete specific simulation by ID");
        System.out.println("2 - Delete ALL history");
        System.out.println("0 - Cancel");
        System.out.print("Choose option: ");

        int choice = -1;
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } else {
            scanner.nextLine();
            return;
        }

        SimulationDAO dao = new SimulationDAO();

        switch (choice) {
            case 1:
                // Delete Single
                System.out.print("Enter ID to delete: ");
                if (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    dao.deleteById(id);
                }
                break;
            case 2:
                // Delete All (With Confirmation)
                System.out.print("Are you sure? This deletes EVERYTHING. (y/n): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    dao.deleteAll();
                } else {
                    System.out.println("Operation cancelled.");
                }
                break;
            case 0:
                System.out.println("Returning to menu...");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }


}
