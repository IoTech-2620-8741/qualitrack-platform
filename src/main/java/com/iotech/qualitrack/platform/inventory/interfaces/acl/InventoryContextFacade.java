package com.iotech.qualitrack.platform.inventory.interfaces.acl;

import com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit;

import java.math.BigDecimal;

/**
 * Boundary for atomic stock consumption by Product Batch Management.
 * The implementation validates tenant ownership and records movements under a pessimistic lock.
 * A repeated operationId must return its original result; reuse with different input must fail.
 */
public interface InventoryContextFacade {
    /**
     * Consumes stock of a raw material lot for a product batch.
     *
     * @param request the lot, product batch, amount and idempotency key
     * @return the stock change of the lot
     * @throws com.iotech.qualitrack.platform.shared.application.result.ApplicationException NOT_FOUND when the lot is
     * not in the laboratory, CONFLICT when it cannot be used, lacks stock or the operation id was used with other values
     */
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

    record Consumption(Long rawMaterialBatchId, Long productBatchId, BigDecimal amountUsed,
                       String unit, BigDecimal stockBefore, BigDecimal stockAfter, String operationId) { }
}
