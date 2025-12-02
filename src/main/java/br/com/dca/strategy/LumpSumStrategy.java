package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;

import java.math.BigDecimal;
import java.util.List;

public class LumpSumStrategy implements InvestmentStrategy {
    public BigDecimal calculate (List<PriceRecord> prices, BigDecimal amount) {
        return null;
    }
}
