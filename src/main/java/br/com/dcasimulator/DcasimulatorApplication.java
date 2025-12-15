package br.com.dcasimulator;

import br.com.dcasimulator.entity.Asset;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.repository.AssetRepository;
import br.com.dcasimulator.repository.SimulationResultRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DcasimulatorApplication {

    public static void main(String[] args) {

        SpringApplication.run(DcasimulatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(SimulationResultRepository repository) {
        return (args) -> {
            SimulationResult testResult = new SimulationResult(
                    "DCA",          // Strategy Type
                    "Bitcoin",      // Asset Name
                    7200.00,        // Invested
                    25116.68,       // Final Value
                    17916.68,       // Profit
                    248.84          // Gain Percent
                    // Date is set automatically in the constructor!
            );

            repository.save(testResult);
            System.out.println("✅ Saved legacy-style simulation!");
        };
    }

    @Bean
    public CommandLineRunner loadAssets(AssetRepository repository) {
        return (args) -> {
            repository.save(new Asset("BTC", "Bitcoin"));
            repository.save(new Asset("ETH", "Ethereum"));
            repository.save(new Asset("XRP", "Ripple"));
            repository.save(new Asset("BNB", "Binance Coin"));
            repository.save(new Asset("SOL", "Solana"));

            System.out.println("✅ Assets loaded successfully!");
        };
    }
}
