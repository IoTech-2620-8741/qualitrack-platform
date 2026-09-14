package com.iotech.qualitrack.platform.inventory.domain.model.valueobjects;
import java.math.BigDecimal;
public record MaterialStockSummary(Long id, Long laboratoryId, String code, String name, String unit,
    BigDecimal minimumStock, BigDecimal usableStock, BigDecimal physicalStock, Long legacyId) { }
