package br.com.dcasimulator.strategy;

import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.entity.SimulationResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("LUMP_SUM")
public class LumpSumStrategy implements InvestmentStrategy {
    @Override
    public SimulationResult calculate (List<PriceRecord> prices, BigDecimal amount) {
        if(prices == null || prices.isEmpty()) {
            return new SimulationResult("DCA", null, 0.0, 0.0, 0.0, 0.0);
        }

        prices.sort(Comparator.comparing(PriceRecord::getDate));

        // Filter out records with zero or negative prices
        List<PriceRecord> validPrices = prices.stream()
                .filter(p -> p.getClose() != null && p.getClose().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        if(validPrices.isEmpty()) {
            return new SimulationResult("DCA", null, 0.0, 0.0, 0.0, 0.0);
        }

        BigDecimal initialPrice = validPrices.get(0).getClose();
        BigDecimal cryptoAccumulated = amount.divide(initialPrice, 8, RoundingMode.HALF_UP);

        BigDecimal finalPrice = validPrices.get(validPrices.size()-1).getClose();
        BigDecimal finalPortfolioValue = cryptoAccumulated.multiply(finalPrice);

        BigDecimal profit = finalPortfolioValue.subtract(amount);
        BigDecimal percentGain = BigDecimal.ZERO;

        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal gainRatio = profit.divide(amount, 4, RoundingMode.HALF_UP);
            percentGain = gainRatio.multiply(new BigDecimal("100"));
        }

        return new SimulationResult(
                "LUMP",
                null,
                amount.doubleValue(),
                finalPortfolioValue.doubleValue(),
                profit.doubleValue(),
                percentGain.doubleValue()
        );
    }
}
