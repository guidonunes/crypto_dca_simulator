package br.com.dcasimulator.strategy;

import br.com.dcasimulator.model.PriceRecord;
import br.com.dcasimulator.entity.SimulationResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("DCA")
public class DcaStrategy implements InvestmentStrategy {
    @Override
    public SimulationResult calculate(List<PriceRecord> prices, BigDecimal investmentAmount) {
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

        BigDecimal totalCryptoAccumulated = BigDecimal.ZERO;
        BigDecimal totalCashInvested =  BigDecimal.ZERO;
        LocalDate nextBuyDate = validPrices.get(0).getDate();

        for (PriceRecord record : validPrices) {
            LocalDate currentDate = record.getDate();
            if(!currentDate.isBefore(nextBuyDate)) {
                BigDecimal cryptoBought = investmentAmount.divide(
                        record.getClose(),
                        8,
                        RoundingMode.HALF_UP
                );
                totalCryptoAccumulated = totalCryptoAccumulated.add(cryptoBought);
                totalCashInvested = totalCashInvested.add(investmentAmount);

                nextBuyDate = nextBuyDate.plusDays(30);
            }
        }

        BigDecimal lastPrice = validPrices.get(validPrices.size()-1).getClose();
        BigDecimal finalPortfolioValue = totalCryptoAccumulated.multiply(lastPrice);
        BigDecimal profit = BigDecimal.ZERO;
        BigDecimal percentGain = BigDecimal.ZERO;

        if (totalCashInvested.compareTo(BigDecimal.ZERO) > 0) {
            profit = finalPortfolioValue.subtract(totalCashInvested);
            BigDecimal gainRatio = profit.divide(totalCashInvested, 4, RoundingMode.HALF_UP);
            percentGain = gainRatio.multiply(new BigDecimal("100"));
        }

        return new SimulationResult(
                "DCA",
                null,
                totalCashInvested.doubleValue(),
                finalPortfolioValue.doubleValue(),
                profit.doubleValue(),
                percentGain.doubleValue()
        );
    }
}