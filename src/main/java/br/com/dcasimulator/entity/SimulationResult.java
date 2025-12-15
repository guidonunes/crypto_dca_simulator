package br.com.dcasimulator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SimulationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalInvested;
    private double totalValue;
    private double profitOrLoss;
    private double profitPercentage;

    public SimulationResult() {}

    public SimulationResult(double totalInvested, double totalValue, double profitOrLoss, double profitPercentage) {
        this.totalInvested = totalInvested;
        this.totalValue = totalValue;
        this.profitOrLoss = profitOrLoss;
        this.profitPercentage = profitPercentage;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public double getTotalInvested() { return totalInvested; }

    public void setTotalInvested(double totalInvested) { this.totalInvested = totalInvested; }

    public double getTotalValue() { return totalValue; }

    public void setTotalValue(double totalValue) { this.totalValue = totalValue; }

    public double getProfitOrLoss() { return profitOrLoss; }

    public void setProfitOrLoss(double profitOrLoss) { this.profitOrLoss = profitOrLoss; }

    public double getProfitPercentage() { return profitPercentage; }

    public void setProfitPercentage(double profitPercentage) { this.profitPercentage = profitPercentage; }
}
