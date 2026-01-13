package br.com.dcasimulator.dto;

import java.math.BigDecimal;

public record MonthlyData(
        int month,
        BigDecimal investedAmount,
        BigDecimal portfolioValue
){}
