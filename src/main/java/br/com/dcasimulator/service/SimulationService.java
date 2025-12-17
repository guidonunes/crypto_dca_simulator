package br.com.dcasimulator.service;


import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.model.PriceRecord;
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

    public SimulationService(SimulationResultRepository repository,
                             DcaStrategy dcaStrategy,
                             LumpSumStrategy lumpSumStrategy) {
        this.repository = repository;
        this.dcaStrategy = dcaStrategy;
        this.lumpSumStrategy = lumpSumStrategy;
    }

    public SimulationResult runDca(List<PriceRecord> prices, BigDecimal amount, String assetName) {
        SimulationResult result = dcaStrategy.calculate(prices, amount);
        result.setAssetName(assetName);
        return repository.save(result);
    }

    public SimulationResult runLumpSum(List<PriceRecord> prices, BigDecimal amount, String assetName) {
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
