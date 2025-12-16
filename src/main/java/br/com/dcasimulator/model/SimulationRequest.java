package br.com.dcasimulator.model;

import java.math.BigDecimal;

public record SimulationRequest(
        String assetName,
        BigDecimal amount,
        String strategy
) {}
