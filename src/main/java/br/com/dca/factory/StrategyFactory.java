package br.com.dca.factory;

import br.com.dca.strategy.DcaStrategy;
import br.com.dca.strategy.InvestmentStrategy;
import br.com.dca.strategy.LumpSumStrategy;

public class StrategyFactory {

    public static InvestmentStrategy getStrategy(String strategyType) {
        if (strategyType == null) {
            throw new IllegalArgumentException("Strategy type must not be null");
        }

        return switch (strategyType.toUpperCase()) {
            case "DCA" -> new DcaStrategy();
            case "LUMP" -> new LumpSumStrategy();
            default -> throw new IllegalArgumentException("Strategy type " + strategyType + " not supported");
        };
    }

}
