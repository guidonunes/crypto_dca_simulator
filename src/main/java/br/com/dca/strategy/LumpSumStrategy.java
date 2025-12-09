package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;
import br.com.dca.model.SimulationResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LumpSumStrategy implements InvestmentStrategy {
    @Override
    public SimulationResult calculate (List<PriceRecord> prices, BigDecimal amount) {
        if(prices == null || prices.isEmpty()) {
            return new SimulationResult("LUMP", null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        prices.sort(Comparator.comparing(PriceRecord::getDate));

        // Filter out records with zero or negative prices
        List<PriceRecord> validPrices = prices.stream()
                .filter(p -> p.getClosePrice() != null && p.getClosePrice().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        if(validPrices.isEmpty()) {
            return new SimulationResult("LUMP", null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal initialPrice = validPrices.get(0).getClosePrice();
        BigDecimal cryptoAccumulated = amount.divide(initialPrice, 8, RoundingMode.HALF_UP);

        BigDecimal finalPrice = validPrices.get(validPrices.size()-1).getClosePrice();
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
                amount,
                finalPortfolioValue,
                profit,
                percentGain
        );
    }
}

