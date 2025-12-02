package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class DcaStrategy implements InvestmentStrategy {
    @Override
    public BigDecimal calculate(List<PriceRecord> prices, BigDecimal investmentAmount) {
        if(prices == null || prices.isEmpty()) {
            return BigDecimal.ZERO;
        }
        prices.sort(Comparator.comparing(PriceRecord::getDate));

        BigDecimal totalCryptoAccumulated = BigDecimal.ZERO;
        BigDecimal totalCashInvested =  BigDecimal.ZERO;

        LocalDate nextBuyDate = prices.get(0).getDate();

        System.out.println("\n -- Simulating DCA Strategy --");
        for (PriceRecord record : prices) {
            LocalDate currentDate = record.getDate();

            if(!currentDate.isBefore(nextBuyDate)) {
                BigDecimal cryptoBought = investmentAmount.divide(
                        record.getClosePrice(),
                        8,
                        RoundingMode.HALF_UP
                );

                totalCryptoAccumulated = totalCryptoAccumulated.add(cryptoBought);
                totalCashInvested = totalCashInvested.add(cryptoBought);

                nextBuyDate = nextBuyDate.plusDays(30);
            }
        }

        BigDecimal lastPrice = prices.get(prices.size()-1).getClosePrice();
        BigDecimal finalPortfolioValue = totalCryptoAccumulated.multiply(lastPrice);

        return finalPortfolioValue;
    }
}
