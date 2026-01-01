package br.com.dcasimulator.entity;

import jakarta.persistence.*;

@Entity
@Table(name="assets_available")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="asset_id")
    private Long id;
    private String symbol;
    private String name;

    public Asset() {}
    public Asset(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
