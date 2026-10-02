package com.iotech.qualitrack.platform.inventory.interfaces.acl;

import com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Boundary for receipt selection and atomic stock consumption.
 * The implementation validates tenant ownership and records movements under a pessimistic lock.
 * A repeated operationId must return its original result; reuse with different input must fail.
 */
public interface InventoryContextFacade {
    List<AvailableReceipt> findUsableReceipts(Long laboratoryId, Long rawMaterialId, LocalDate onDate);

    Consumption consume(ConsumptionRequest request);

    /**
     * Checks that a raw material exists in the laboratory and is kept in the environment.
     */
    boolean isRawMaterialInEnvironment(Long laboratoryId, Long environmentId, Long rawMaterialId);

    record ConsumptionRequest(Long laboratoryId, Long rawMaterialBatchId, Long productBatchId,
                              BigDecimal amountUsed, String unit, String operationId) {
        public ConsumptionRequest {
            if (laboratoryId == null || laboratoryId <= 0) throw new IllegalArgumentException("Invalid laboratory ID");
            if (rawMaterialBatchId == null || rawMaterialBatchId <= 0) throw new IllegalArgumentException("Invalid receipt ID");
            if (productBatchId == null || productBatchId <= 0) throw new IllegalArgumentException("Invalid product batch ID");
            if (operationId == null || operationId.isBlank()) throw new IllegalArgumentException("Operation ID is required");
            unit = StockUnit.normalize(unit);
            StockUnit.validateQuantity(amountUsed, unit, false);
            operationId = operationId.trim();
        }
    }

    record AvailableReceipt(Long id, Long rawMaterialId, String batchNumber, String unit,
                            BigDecimal availableAmount, LocalDate expiresOn) { }

    record Consumption(Long rawMaterialBatchId, Long productBatchId, BigDecimal amountUsed,
                       String unit, BigDecimal stockBefore, BigDecimal stockAfter, String operationId) { }
}
