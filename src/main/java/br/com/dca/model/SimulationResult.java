package br.com.dca.model;

import java.math.BigDecimal;

public class SimulationResult {
    private String strategyName;
    private String assetName;
    private BigDecimal initialInvestment;
    private BigDecimal finalValue;
    private BigDecimal percentGain;

    public SimulationResult(String strategyName,String assetName, BigDecimal initialInvestment, BigDecimal finalValue, BigDecimal profit, BigDecimal percentGain) {
        this.strategyName = strategyName;
        this.assetName = assetName;
        this.initialInvestment = initialInvestment;
        this.finalValue = finalValue;
        this.percentGain = percentGain;
    }

    public BigDecimal getProfit() {
        if (finalValue == null || initialInvestment == null) {
            return BigDecimal.ZERO;
        }
        return finalValue.subtract(initialInvestment);
    }

    public String getStrategyName() {
        return strategyName;
    }

    public String getAssetName() {
        return assetName;
    }

    public BigDecimal getInitialInvestment() {
        return initialInvestment;
    }

    public BigDecimal getFinalValue() {
        return finalValue;
    }


    public BigDecimal getPercentGain() {
        return percentGain;
    }
}
