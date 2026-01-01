package br.com.dcasimulator.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "simulations")
public class SimulationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="strategy_type")
    private String strategyType;

    @Column(name="asset_name")
    private String assetName;

    @Column(name="invested_amount")
    private Double investedAmount;

    @Column(name="final_value")
    private Double finalValue;

    private Double profit;

    @Column(name="gain_percent")
    private Double gainPercent;

    @Column(name="simulation_date")
    private LocalDateTime simulationDate;

    public SimulationResult() {}

    public SimulationResult(String strategyType, String assetName, Double investedAmount,
                            Double finalValue, Double profit, Double gainPercent) {
        this.strategyType = strategyType;
        this.assetName = assetName;
        this.investedAmount = investedAmount;
        this.finalValue = finalValue;
        this.profit = profit;
        this.gainPercent = gainPercent;
        this.simulationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(Double investedAmount) {
        this.investedAmount = investedAmount;
    }

    public Double getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Double finalValue) {
        this.finalValue = finalValue;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getGainPercent() {
        return gainPercent;
    }

    public void setGainPercent(Double gainPercent) {
        this.gainPercent = gainPercent;
    }

    public LocalDateTime getSimulationDate() {
        return simulationDate;
    }

    public void setSimulationDate(LocalDateTime simulationDate) {
        this.simulationDate = simulationDate;
    }
}