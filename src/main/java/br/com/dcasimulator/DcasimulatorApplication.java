package br.com.dcasimulator;

import br.com.dcasimulator.entity.Asset;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.repository.AssetRepository;
import br.com.dcasimulator.repository.SimulationResultRepository;
import br.com.dcasimulator.strategy.DcaStrategy;
import br.com.dcasimulator.strategy.LumpSumStrategy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DcasimulatorApplication {

    public static void main(String[] args) {

        SpringApplication.run(DcasimulatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(SimulationResultRepository simRepository,
                                  AssetRepository assetRepository,
                                  LumpSumStrategy lumpSumStrategy,
                                  DcaStrategy dcaStrategy) {
        return (args) -> {
            System.out.println("🚀 Starting Simulation Demo...");
            List<PriceRecord> fakePrices = new ArrayList<>(List.of(
                    new PriceRecord(1, LocalDate.now().minusMonths(1), new BigDecimal("50000")),
                    new PriceRecord(1, LocalDate.now(), new BigDecimal("60000"))
            ));

            BigDecimal amountToInvest = new BigDecimal("10000");

            SimulationResult dcaResult = dcaStrategy.calculate(fakePrices, amountToInvest);
            dcaResult.setAssetName("Bitcoin");
            simRepository.save(dcaResult);
            System.out.println("✅ DCA Simulation Saved! ID: " + dcaResult.getId());

            SimulationResult lumpResult = lumpSumStrategy.calculate(fakePrices, amountToInvest);
            lumpResult.setAssetName("Bitcoin");
            simRepository.save(lumpResult);
            System.out.println("✅ Lump Sum Simulation Saved! ID: " + lumpResult.getId());

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
