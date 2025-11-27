package br.com.dca;
import br.com.dca.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            System.out.println("Successfully connected to database.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
