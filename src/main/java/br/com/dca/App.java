package br.com.dca;
import br.com.dca.factory.ConnectionFactory;
import br.com.dca.model.PriceRecord;
import br.com.dca.util.CsvParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Database connection test
//        try {
//            Connection connection = ConnectionFactory.getConnection();
//            System.out.println("Successfully connected.");
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
        //CsvParser test
        CsvParser csvParser = new CsvParser();
        String btcPath = "src/main/resources/btc_brl_history.csv";

        System.out.println("---1. Testing the CSV Parser");
        List<PriceRecord> btcData = csvParser.parse(btcPath, 1);

        if (btcData.isEmpty()) {
            System.out.println("---1. No data found");
            return;
        }

        System.out.println("Successfuly parsed " + btcData.size() + " data");

        System.out.println("\n First 5 Records --- Verify if it is right");
        for (int i = 0; i < Math.min(5, btcData.size()); i++) {
            PriceRecord record = btcData.get(i);

            System.out.println("Date: " + record.getDate() + " | Price: R$ " + record.getClosePrice());
        }

    }
}
