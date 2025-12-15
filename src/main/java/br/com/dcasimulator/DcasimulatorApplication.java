package br.com.dcasimulator;

import br.com.dcasimulator.entity.SimulationResult;
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
            // 1. Create the data (The "Noun")
            SimulationResult myResult = new SimulationResult(
                    1000.0, // invested
                    1500.0, // value
                    500.0,  // profit
                    50.0    // percentage
            );

            // 2. Save the data (The "Verb")
            repository.save(myResult);

            // 3. Check the Console
            System.out.println("✅ SAVED! New ID is: " + myResult.getId());
        };
    }
}
