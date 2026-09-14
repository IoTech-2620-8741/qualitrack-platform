package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceiptResource(Long id, Long laboratoryId, Long rawMaterialId, String supplier,
        String batchNumber, String unit, BigDecimal initialAmount, BigDecimal availableAmount,
        LocalDate receivedOn, LocalDate expiresOn, String status, boolean usable, String availability) { }
