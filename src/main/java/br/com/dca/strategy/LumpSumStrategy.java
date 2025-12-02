package br.com.dca.strategy;

import br.com.dca.model.PriceRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class LumpSumStrategy implements InvestmentStrategy {
    @Override
    public BigDecimal calculate (List<PriceRecord> prices, BigDecimal amount) {
        if(prices == null || prices.isEmpty()) {
            return BigDecimal.ZERO;
        }

        prices.sort(Comparator.comparing(PriceRecord::getDate));

        System.out.println("\n -- Simulating Lump Sum Strategy --");

        PriceRecord firstRecord = prices.get(0);
        BigDecimal initialPrice = firstRecord.getClosePrice();

        BigDecimal cryptoAccumulated = amount.divide(initialPrice, 8, RoundingMode.HALF_UP);
        PriceRecord lastRecord = prices.get(prices.size()-1);
        BigDecimal finalPrice = lastRecord.getClosePrice();

        BigDecimal finalPortfolioValue = cryptoAccumulated.multiply(finalPrice);

        System.out.println("Date of Buy:    " + firstRecord.getDate());
        System.out.println("Initial Price:  R$ " + initialPrice);
        System.out.println("Total Invested: R$ " + amount);
        System.out.println("Final Value:    R$ " + finalPortfolioValue);

        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profit = finalPortfolioValue.subtract(amount);
            BigDecimal gainRatio = profit.divide(finalPrice, 4, RoundingMode.HALF_UP);
            BigDecimal percentGain = gainRatio.multiply(new BigDecimal("100"));

            System.out.println("Profit/Loss: R$ " + profit);
            System.out.println("Total Gain: " + percentGain + "%");
        }

        return finalPortfolioValue;
    }
}
