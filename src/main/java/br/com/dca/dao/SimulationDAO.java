package br.com.dca.dao;

import br.com.dca.factory.ConnectionFactory;
import br.com.dca.model.SimulationResult;

import java.rmi.ServerError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
