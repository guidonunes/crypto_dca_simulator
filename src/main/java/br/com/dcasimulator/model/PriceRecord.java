package br.com.dcasimulator.model; // <--- Updated to match your new project!

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceRecord {

    private int assetId;
    private LocalDate date;
    private BigDecimal closePrice;

    // Constructor
    public PriceRecord(int assetId, LocalDate date, BigDecimal closePrice) {
        this.assetId = assetId;
        this.date = date;
        this.closePrice = closePrice;
    }

    // Getters
    public int getAssetId() {
        return assetId;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }
}