package br.com.dca.dao;

import br.com.dca.factory.ConnectionFactory;
import br.com.dca.model.SimulationResult;

import java.math.BigDecimal;
import java.rmi.ServerError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimulationDAO {
    public void save(SimulationResult result, String assetName) {
        String sql = "INSERT INTO simulations " +
                "(strategy_type, asset_name, invested_amount, final_value, gain_percent) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setString(1, result.getStrategyName());
             stmt.setString(2, assetName);
             stmt.setBigDecimal(3, result.getInitialInvestment());
             stmt.setBigDecimal(4, result.getFinalValue());
             stmt.setBigDecimal(5, result.getPercentGain());

             stmt.execute();
            System.out.println(">> [Database] Simulation saved successfully");

        } catch (SQLException e) {
            System.err.println("Error saving simulation: " + e.getMessage());
        }
    }

    public List<SimulationResult> findAll() {
        String sql = "SELECT * FROM simulations ORDER BY simulation_date DESC";
        List<SimulationResult> history = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String strategy = rs.getString("strategy_type");
                String assetName = rs.getString("asset_name");
                BigDecimal initialInvestment = rs.getBigDecimal("invested_amount");
                BigDecimal finalValue = rs.getBigDecimal("final_value");
                BigDecimal profit = rs.getBigDecimal("profit");
                BigDecimal percentGain = rs.getBigDecimal("gain_percent");

                SimulationResult result = new SimulationResult(strategy, assetName, initialInvestment, finalValue, profit, percentGain);
                history.add(result);
            }


        } catch (SQLException e) {
            System.err.println("Error fetching simulations: " + e.getMessage());
        }
        return history;
    }
}
