package br.com.dcasimulator.runner;

import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.service.SimulationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {

    private final SimulationService service;

    public ConsoleApp(SimulationService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------------------");
        System.out.println("💰 Welcome to the Crypto DCA Simulator! 💰");
        System.out.println("-----------------------------------------");

        boolean running = true;

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Run DCA Simulation");
            System.out.println("2. Run Lump Sum Simulation");
            System.out.println("3. View Simulation History");
            System.out.println("4. Delete a Simulation");
            System.out.println("0. Exit");
            System.out.print("> ");

            int choice = scanner.nextInt();

            // The Switch Statement: Clean & readable routing
            switch (choice) {
                case 1, 2 -> handleNewSimulation(scanner, choice);
                case 3 -> viewHistory();
                case 4 -> deleteSimulation(scanner);
                case 0 -> {
                    System.out.println("Goodbye! 👋");
                    running = false;
                }
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    // --- HELPER METHODS ---

    private void handleNewSimulation(Scanner scanner, int choice) {
        System.out.print("Enter Asset Name (e.g. Bitcoin): ");
        String assetName = scanner.next();

        System.out.print("Enter Amount to Invest: ");
        BigDecimal amount = scanner.nextBigDecimal();

        // Fake Data Generator (We will replace this with an API later)
        List<PriceRecord> fakePrices = new ArrayList<>();
        fakePrices.add(new PriceRecord(1, LocalDate.now().minusMonths(6), new BigDecimal("50000")));
        fakePrices.add(new PriceRecord(1, LocalDate.now().minusMonths(3), new BigDecimal("45000")));
        fakePrices.add(new PriceRecord(1, LocalDate.now(), new BigDecimal("60000")));

        SimulationResult result;
        if (choice == 1) {
            result = service.runDca(fakePrices, amount, assetName);
        } else {
            result = service.runLumpSum(fakePrices, amount, assetName);
        }

        printResult(result);
    }

    private void viewHistory() {
        List<SimulationResult> history = service.getAllResults();

        if (history.isEmpty()) {
            System.out.println("\n⚠️ No history found.");
            return;
        }

        System.out.println("\n--- History (" + history.size() + " records) ---");
        for (SimulationResult r : history) {
            System.out.printf("ID: %d | %s | %s | Profit: $%.2f%n",
                    r.getId(), r.getStrategyType(), r.getAssetName(), r.getProfit());
        }
    }

    private void deleteSimulation(Scanner scanner) {
        System.out.print("Enter ID to delete: ");
        Long id = scanner.nextLong();

        try {
            service.deleteResult(id);
            System.out.println("✅ Record " + id + " deleted.");
        } catch (Exception e) {
            System.out.println("❌ Could not delete record. Check if ID exists.");
        }
    }

    private void printResult(SimulationResult result) {
        System.out.println("\n✅ Simulation Complete!");
        System.out.println("Strategy:    " + result.getStrategyType());
        System.out.println("Invested:    $" + result.getInvestedAmount());
        System.out.println("Final Value: $" + result.getFinalValue());
        System.out.println("Profit:      $" + result.getProfit());
        System.out.println("Gain:        " + result.getGainPercent() + "%");
    }
}