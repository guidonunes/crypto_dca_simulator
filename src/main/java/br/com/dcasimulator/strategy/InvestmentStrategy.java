package br.com.dcasimulator.strategy;


import br.com.dcasimulator.entity.Price;
import br.com.dcasimulator.entity.SimulationResult;
import br.com.dcasimulator.model.PriceRecord;

import java.math.BigDecimal;
import java.util.List;

public interface InvestmentStrategy {
    SimulationResult calculate (List<Price> prices, BigDecimal investmentAmount);
}