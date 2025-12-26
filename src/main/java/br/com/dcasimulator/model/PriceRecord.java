package br.com.dcasimulator.model; // <--- Updated to match your new project!

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceRecord {

    private LocalDate date;
    private BigDecimal close;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;

    // Constructor
    public PriceRecord(LocalDate date, BigDecimal close, BigDecimal open, BigDecimal high, BigDecimal low) {
        this.date = date;
        this.close = close;
        this.open = open;
        this.high = high;
        this.low = low;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    @Override
    public String toString() {
        return "PriceRecord{" +
                "date=" + date +
                ", close=" + close +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}


