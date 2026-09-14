package com.iotech.qualitrack.platform.inventory.domain.model.commands;
public record SaveRawMaterialCommand(Long laboratoryId, Long materialId, String code, String name, String unit, java.math.BigDecimal minimumStock) { }
