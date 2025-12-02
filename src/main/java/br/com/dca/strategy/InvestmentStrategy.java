package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;
import java.math.BigDecimal;
import java.util.List;

public interface InvestmentStrategy {
    BigDecimal calculate (List<PriceRecord> prices, BigDecimal investmentAmount);
}
