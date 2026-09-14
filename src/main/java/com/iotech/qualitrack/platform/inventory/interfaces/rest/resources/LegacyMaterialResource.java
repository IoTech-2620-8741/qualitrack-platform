package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;

import java.math.BigDecimal;

public record LegacyMaterialResource(
        Long id, String code, String name, String unit, BigDecimal minimumStock, String supplier, String batchNumber, String expiresOn, BigDecimal balance) { }
