package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;
import br.com.dca.model.SimulationResult;

import java.math.BigDecimal;
import java.util.List;

public interface InvestmentStrategy {
    SimulationResult calculate (List<PriceRecord> prices, BigDecimal investmentAmount);
}
