package br.com.dcasimulator.controller;


import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.model.SimulationRequest;
import br.com.dcasimulator.service.SimulationService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/simulations")
public class SimulationController {
    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping
    public SimulationResult runSimulation(@RequestBody SimulationRequest request) {
        List<PriceRecord> fakePrices = new ArrayList<>();
        fakePrices.add(new PriceRecord(1, LocalDate.now().minusMonths(6), new BigDecimal("50000")));
        fakePrices.add(new PriceRecord(1, LocalDate.now().minusMonths(3), new BigDecimal("45000")));
        fakePrices.add(new PriceRecord(1, LocalDate.now(), new BigDecimal("60000")));

        if ("DCA".equalsIgnoreCase(request.strategy())) {
            return simulationService.runDca(fakePrices, request.amount(), request.assetName());
        } else {
            return simulationService.runLumpSum(fakePrices, request.amount(), request.assetName());
        }
    }

    @GetMapping
    public List<SimulationResult> getAllSimulations() {
        return simulationService.getAllResults();
    }


}
