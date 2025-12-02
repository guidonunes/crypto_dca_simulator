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

        System.out.println("Total buys executed: " + buyCount);
        BigDecimal lastPrice = prices.get(prices.size()-1).getClosePrice();
        BigDecimal finalPortfolioValue = totalCryptoAccumulated.multiply(lastPrice);

        System.out.println("Total Invested: R$ " + totalCashInvested);
        System.out.println("Final Portfolio: R$ " + finalPortfolioValue);

        if (totalCashInvested.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profit = finalPortfolioValue.subtract(totalCashInvested);
            BigDecimal gainRatio = profit.divide(totalCashInvested, 4, RoundingMode.HALF_UP);
            BigDecimal percentGain = gainRatio.multiply(new BigDecimal("100"));

            System.out.println("Profit/Loss: R$ " + profit);
            System.out.println("Percent Gain: " + percentGain + "%");
        }

        return finalPortfolioValue;
    }
}
