package br.com.dcasimulator.runner;

import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.model.PriceRecord;
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

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Run DCA Simulation");
            System.out.println("2. Run Lump Sum Simulation");
            System.out.println("0. Exit");
            System.out.print("> ");

            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Goodbye! ");
                break;
            }

            // Common inputs for both strategies
            System.out.print("Enter Asset Name (e.g. Bitcoin): ");
            String assetName = scanner.next();

            System.out.print("Enter Amount to Invest: ");
            BigDecimal amount = scanner.nextBigDecimal();

            // --- FAKE DATA GENERATOR (Until we build the Price API) ---
            List<PriceRecord> fakePrices = new ArrayList<>();
            fakePrices.add(new PriceRecord(1, LocalDate.now().minusMonths(6), new BigDecimal("50000")));
            fakePrices.add(new PriceRecord(1, LocalDate.now().minusMonths(3), new BigDecimal("45000")));
            fakePrices.add(new PriceRecord(1, LocalDate.now(), new BigDecimal("60000")));
            // ----------------------------------------------------------

            if (choice == 1) {
                SimulationResult result = service.runDca(fakePrices, amount, assetName);
                printResult(result);
            } else if (choice == 2) {
                SimulationResult result = service.runLumpSum(fakePrices, amount, assetName);
                printResult(result);
            } else {
                System.out.println("❌ Invalid option.");
            }
        }
    }

    private void printResult(SimulationResult result) {
        System.out.println("\n✅ Simulation Complete!");
        System.out.println("Strategy: " + result.getStrategyType());
        System.out.println("Invested: $" + result.getInvestedAmount());
        System.out.println("Final Value: $" + result.getFinalValue());
        System.out.println("Profit: $" + result.getProfit());
    }
}
