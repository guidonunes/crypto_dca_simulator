package br.com.dca.dao;

import br.com.dca.factory.ConnectionFactory;
import br.com.dca.model.PriceRecord;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AssetPriceDAO {
    private Connection connection;
    public AssetPriceDAO() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }

    public void bulkInsertPrices (List<PriceRecord> prices) {
        String sql = "INSERT INTO price_history(asset_id, price_date, close_price) VALUES (?, ?, ? )";
        final int BATCH_SIZE = 1000;

        try (PreparedStatement pstm = this.connection.prepareStatement(sql)) {
            this.connection.setAutoCommit(false);
            int count = 0;
            System.out.println("Starting bulk insert of about " + prices.size() + " records...");

            for (PriceRecord price : prices) {
                pstm.setInt(1, price.getAssetId());
                pstm.setDate(2, Date.valueOf(price.getDate()));
                pstm.setBigDecimal(3, price.getClosePrice());

                pstm.addBatch();

                if(++count % BATCH_SIZE == 0) {
                    pstm.executeBatch();
                    System.out.println("Saved chunk of " + BATCH_SIZE + " records in bulk insert...");
                }
            }
            pstm.executeBatch();
            this.connection.commit();
            System.out.println("Transaction commited. Total inserted: " + count);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Rolling back...Error detected");
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                this.connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
