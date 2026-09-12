package com.iotech.qualitrack.platform.batch.domain.model.commands;

/**
 * Command to request linking a raw material usage record to a production batch.
 *
 * @param batchId The numeric identifier of the batch. Cannot be null or less than 1.
 * @param rawMaterialId The numeric identifier of the consumed raw material. Cannot be null or less than 1.
 * @param quantityUsed The amount of raw material consumed. Cannot be null or less than or equal to 0.
 */
public record LinkRawMaterialCommand(
        Long batchId,
        Long rawMaterialId,
        Double quantityUsed,
        String unit
) {
    /**
     * Compact constructor for LinkRawMaterialCommand.
     * Enforces Fail-Fast validation.
     */
    public LinkRawMaterialCommand {
        if (unit == null || unit.isBlank()) throw new IllegalArgumentException("Consumption unit is required");
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("batchId cannot be null or less than 1");
        }
        if (rawMaterialId == null || rawMaterialId <= 0) {
            throw new IllegalArgumentException("rawMaterialId cannot be null or less than 1");
        }
        if (quantityUsed == null || !Double.isFinite(quantityUsed) || quantityUsed <= 0) {
            throw new IllegalArgumentException("quantityUsed cannot be null or less than or equal to 0");
        }
    }
}
