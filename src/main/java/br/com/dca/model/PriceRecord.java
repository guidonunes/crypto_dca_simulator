package br.com.dca.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceRecord {
    private int assetId;
    private LocalDate date;
    private BigDecimal closePrice;

    public PriceRecord(int assetId, LocalDate date, BigDecimal closePrice) {
        this.assetId = assetId;
        this.date = date;
        this.closePrice = closePrice;
    }

    public int getAssetId() { return assetId; }
    public LocalDate getDate() { return date; }
    public BigDecimal getClosePrice() { return closePrice; }
}
