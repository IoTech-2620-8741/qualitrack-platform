package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AvailableReceiptResource(
        Long id, Long rawMaterialId, String batchNumber, String unit, BigDecimal availableAmount, LocalDate expiresOn) { }
