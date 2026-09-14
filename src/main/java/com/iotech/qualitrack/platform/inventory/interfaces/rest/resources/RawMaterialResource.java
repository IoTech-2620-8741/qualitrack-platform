package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;
import java.math.BigDecimal;
public record RawMaterialResource(Long id, Long laboratoryId, String code, String name, String unit,
    BigDecimal minimumStock, BigDecimal usableStock, BigDecimal physicalStock, Long legacyId) { }
