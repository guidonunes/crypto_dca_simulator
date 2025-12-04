package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;
import br.com.dca.model.SimulationResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

public class LumpSumStrategy implements InvestmentStrategy {
    @Override
    public SimulationResult calculate (List<PriceRecord> prices, BigDecimal amount) {
        if(prices == null || prices.isEmpty()) {
            return new SimulationResult("LUMP", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        prices.sort(Comparator.comparing(PriceRecord::getDate));

        BigDecimal initialPrice = prices.get(0).getClosePrice();
        BigDecimal cryptoAccumulated = amount.divide(initialPrice, 8, RoundingMode.HALF_UP);

        BigDecimal finalPrice = prices.get(prices.size()-1).getClosePrice();
        BigDecimal finalPortfolioValue = cryptoAccumulated.multiply(finalPrice);

        BigDecimal profit = finalPortfolioValue.subtract(amount);
        BigDecimal percentGain = BigDecimal.ZERO;

        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal gainRatio = profit.divide(amount, 4, RoundingMode.HALF_UP);
            percentGain = gainRatio.multiply(new BigDecimal("100"));
        }

        return new SimulationResult(
                "LUMP",
                amount,
                finalPortfolioValue,
                profit,
                percentGain
        );
    }
}
