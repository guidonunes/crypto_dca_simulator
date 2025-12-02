package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;

import java.math.BigDecimal;
import java.util.List;

public class DcaStrategy implements InvestmentStrategy {
    @Override
    public BigDecimal calculate(List<PriceRecord> prices, BigDecimal investmentAmount) {
        if(prices == null || prices.isEmpty()) {
            return BigDecimal.ZERO;
        }
    }
}
