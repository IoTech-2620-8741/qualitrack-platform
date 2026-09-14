package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;

import java.math.BigDecimal;

public record ReceiptConsumptionResource(
        Long rawMaterialBatchId, Long productBatchId, BigDecimal amountUsed, String unit, BigDecimal stockBefore, BigDecimal stockAfter, String operationId) { }
