package com.iotech.qualitrack.platform.inventory.domain.model.commands;
import com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit;
import java.math.BigDecimal;

public record ConsumeRawMaterialBatchCommand(Long laboratoryId, Long rawMaterialBatchId, Long productBatchId,
        BigDecimal amountUsed, String unit, String operationId) {
    public ConsumeRawMaterialBatchCommand {
        if (laboratoryId == null || laboratoryId <= 0 || rawMaterialBatchId == null || rawMaterialBatchId <= 0
            || productBatchId == null || productBatchId <= 0) throw new IllegalArgumentException("Positive inventory and batch IDs are required");
        if (operationId == null || operationId.isBlank() || operationId.trim().length() > 100)
            throw new IllegalArgumentException("Operation ID is required (maximum 100 characters)");
        unit = StockUnit.normalize(unit);
        StockUnit.validateQuantity(amountUsed, unit, false);
        operationId = operationId.trim();
    }
}
