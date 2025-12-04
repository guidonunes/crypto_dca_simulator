package br.com.dca.model;

import java.math.BigDecimal;

public class SimulationResult {
    private String strategyName;
    private BigDecimal initialInvestment;
    private BigDecimal finalValue;
    private BigDecimal profit;
    private BigDecimal percentGain;

    public SimulationResult(String strategyName, BigDecimal initialInvestment, BigDecimal finalValue, BigDecimal profit, BigDecimal percentGain) {
        this.strategyName = strategyName;
        this.initialInvestment = initialInvestment;
        this.finalValue = finalValue;
        this.profit = profit;
        this.percentGain = percentGain;
    }
}
