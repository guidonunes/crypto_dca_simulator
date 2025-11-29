package br.com.dca.view;

import br.com.dca.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddCryptoView {
    public static void main(String[] args) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement stm = connection.createStatement();
            stm.executeUpdate("INSERT INTO assets (symbol, name) VALUES('SOL', 'Solana')");
            System.out.println("Successfully added asset");
            stm.close();
            connection.close();
    } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
