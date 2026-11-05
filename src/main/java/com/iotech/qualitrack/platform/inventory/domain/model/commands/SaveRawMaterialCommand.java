package com.iotech.qualitrack.platform.inventory.domain.model.commands;

import java.math.BigDecimal;
/**
 * Registers (materialId null) or updates the catalog data of a raw material kept in an environment.
 */
public record SaveRawMaterialCommand(
        Long laboratoryId,
        Long environmentId,
        Long materialId,
        String code,
        String name,
        String unit,
        BigDecimal minimumStock
) { }
