package com.iotech.qualitrack.platform.inventory.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Registers a supplier receipt (raw material lot) of a raw material kept in an environment.
 */
public record ReceiveRawMaterialBatchCommand(
        Long laboratoryId,
        Long environmentId,
        Long materialId,
        String supplier,
        String batchNumber,
        String unit,
        BigDecimal amount,
        LocalDate receivedOn,
        LocalDate expiresOn
) { }
