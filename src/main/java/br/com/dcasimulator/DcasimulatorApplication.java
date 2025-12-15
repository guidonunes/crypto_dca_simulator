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
