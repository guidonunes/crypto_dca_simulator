package br.com.dcasimulator.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SimulationRequest(
        String assetName,
        BigDecimal amount,
        String strategy,
        LocalDate startDate
) {}
