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
        return simulationService.runSimulation(request);
    }

    @GetMapping
    public List<SimulationResult> getAllSimulations() {
        return simulationService.getAllResults();
    }

    @DeleteMapping("/{id}")
    public void deleteSimulation(@PathVariable Long id) {
        simulationService.deleteResult(id);
    }

    @DeleteMapping
    public void deleteAll() {
        simulationService.deleteAllResults();
    }

}
