package com.iotech.qualitrack.platform.inventory.domain.model.valueobjects;
import java.math.BigDecimal;
public record ReceiptConsumption(Long rawMaterialBatchId, Long productBatchId, BigDecimal amountUsed,
    String unit, BigDecimal stockBefore, BigDecimal stockAfter, String operationId) { }
