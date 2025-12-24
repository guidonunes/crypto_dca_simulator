package br.com.dcasimulator.service;


import br.com.dcasimulator.entity.Price;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.model.SimulationRequest;
import br.com.dcasimulator.repository.AssetRepository;
import br.com.dcasimulator.repository.PriceRepository;
import br.com.dcasimulator.repository.SimulationResultRepository;
import br.com.dcasimulator.strategy.DcaStrategy;
import br.com.dcasimulator.strategy.LumpSumStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public SimulationResult runSimulation(SimulationRequest request) {
        List<Price> realPrices = priceRepository.findByAssetSymbol(request.assetName());
        if (realPrices.isEmpty()) {
            throw new RuntimeException("No history found for asset: " + request.assetName());
        }

        // 3. Route to Strategy
        // Note: We now pass 'realPrices' which is List<Price>
        if ("DCA".equalsIgnoreCase(request.strategy())) {
            return runDca(realPrices, request.amount(), request.assetName());
        } else {
            return runLumpSum(realPrices, request.amount(), request.assetName());
        }
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
