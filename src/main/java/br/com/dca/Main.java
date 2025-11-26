package br.com.dca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "RM562938", "250191");
            System.out.println("Successfuly connected to database.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
