package br.com.dcasimulator.dto;

import java.math.BigDecimal;
import java.util.List;

public record SimulationResponse(
        String assetName,
        BigDecimal investedAmount,
        BigDecimal finalValue,
        BigDecimal profit,
        BigDecimal gainPercent,
        List<MonthlyData> chartData // <--- This is where MonthlyData is used!
) {}