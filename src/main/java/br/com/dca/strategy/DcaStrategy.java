package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;
import br.com.dca.model.SimulationResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class DcaStrategy implements InvestmentStrategy {
    @Override
    public SimulationResult calculate(List<PriceRecord> prices, BigDecimal investmentAmount) {
        if(prices == null || prices.isEmpty()) {
            return new SimulationResult("DCA", null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }
        prices.sort(Comparator.comparing(PriceRecord::getDate));

        BigDecimal totalCryptoAccumulated = BigDecimal.ZERO;
        BigDecimal totalCashInvested =  BigDecimal.ZERO;
        LocalDate nextBuyDate = prices.get(0).getDate();

        int buyCount = 0;
        for (PriceRecord record : prices) {
            LocalDate currentDate = record.getDate();
            if(!currentDate.isBefore(nextBuyDate)) {
                BigDecimal cryptoBought = investmentAmount.divide(
                        record.getClosePrice(),
                        8,
                        RoundingMode.HALF_UP
                );
                totalCryptoAccumulated = totalCryptoAccumulated.add(cryptoBought);
                totalCashInvested = totalCashInvested.add(investmentAmount);
                buyCount++;
                nextBuyDate = nextBuyDate.plusDays(30);
            }
        }

        BigDecimal lastPrice = prices.get(prices.size()-1).getClosePrice();
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
                totalCashInvested,
                finalPortfolioValue,
                profit,
                percentGain
        );
    }
}
