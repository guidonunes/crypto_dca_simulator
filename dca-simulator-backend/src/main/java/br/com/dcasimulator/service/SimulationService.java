package br.com.dcasimulator.service;


import br.com.dcasimulator.dto.MonthlyData;
import br.com.dcasimulator.dto.SimulationResponse;
import br.com.dcasimulator.entity.Price;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.exception.ResourceNotFoundException;
import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.model.SimulationRequest;
import br.com.dcasimulator.repository.AssetRepository;
import br.com.dcasimulator.repository.PriceRepository;
import br.com.dcasimulator.repository.SimulationResultRepository;
import br.com.dcasimulator.strategy.DcaStrategy;
import br.com.dcasimulator.strategy.LumpSumStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {
    private final SimulationResultRepository repository;
    private final DcaStrategy dcaStrategy;
    private final LumpSumStrategy lumpSumStrategy;
    private final PriceRepository priceRepository;

    public SimulationService(SimulationResultRepository repository,
                             DcaStrategy dcaStrategy,
                             LumpSumStrategy lumpSumStrategy,
                             PriceRepository priceRepository) {
        this.repository = repository;
        this.dcaStrategy = dcaStrategy;
        this.lumpSumStrategy = lumpSumStrategy;
        this.priceRepository = priceRepository;
    }

    public SimulationResponse runSimulation(SimulationRequest request) {
        List<Price> allPrices = priceRepository.findByAssetSymbol(request.assetName());
        if (allPrices.isEmpty()) {
            throw new ResourceNotFoundException("Asset not found: " + request.assetName());
        }

        if(request.startDate() != null) {
            LocalDate firstDbDate = allPrices.get(allPrices.size() - 1).getDate();

            if(firstDbDate.isAfter(request.startDate())) {
                throw new IllegalArgumentException(
                        "Invalid Start Date! You asked for " + request.startDate() +
                                ", but our history for " + request.assetName() +
                                " only starts on " + firstDbDate
                );
            }
        }

        SimulationResult result;

        if ("DCA".equalsIgnoreCase(request.strategy())) {
            result = runDca(allPrices, request.amount(), request.assetName());
        } else {
            result = runLumpSum(allPrices, request.amount(), request.assetName());
        }

        List<MonthlyData> chartData = new ArrayList<>();

        return new SimulationResponse(
                result.getAssetName(),
                BigDecimal.valueOf(result.getInvestedAmount()), // Converting Double -> BigDecimal
                BigDecimal.valueOf(result.getFinalValue()),
                BigDecimal.valueOf(result.getProfit()),
                BigDecimal.valueOf(result.getGainPercent()),
                chartData
        );

    }

    public SimulationResult runDca(List<Price> prices, BigDecimal amount, String assetName) {
        SimulationResult result = dcaStrategy.calculate(prices, amount);
        result.setAssetName(assetName);
        return repository.save(result);
    }

    public SimulationResult runLumpSum(List<Price> prices, BigDecimal amount, String assetName) {
        SimulationResult result = lumpSumStrategy.calculate(prices, amount);
        result.setAssetName(assetName);
        return repository.save(result);
    }

    public List<SimulationResult> getAllResults() {
        return repository.findAll();
    }

    public void deleteResult(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllResults() {
        repository.deleteAll();
    }
}
