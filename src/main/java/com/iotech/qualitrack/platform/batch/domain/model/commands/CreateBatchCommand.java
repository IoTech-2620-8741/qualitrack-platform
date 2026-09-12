package com.iotech.qualitrack.platform.batch.domain.model.commands;

/**
 * Command to request the creation of a new production batch.
 *
 * @param labId The numeric identifier of the laboratory. Cannot be null or less than 1.
 * @param productId The numeric identifier of the product to manufacture. Cannot be null or less than 1.
 * @param batchNumber The traceability code assigned to the batch. Cannot be null or blank.
 * @param quantity The intended amount to produce. Cannot be null or less than or equal to 0.
 * @param startDate The scheduled start date of the batch. Cannot be null or blank.
 * @param notes Optional manufacturing or quality control notes.
 */
public record CreateBatchCommand(
        Long labId,
        Long productId,
        String batchNumber,
        Double quantity,
        String startDate,
        String notes,
        String unit
) {
    /**
     * Compact constructor for CreateBatchCommand.
     * Enforces Fail-Fast validation.
     */
    public CreateBatchCommand {
        unit = com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit.normalize(unit == null ? "units" : unit);
        if (labId == null || labId <= 0) {
            throw new IllegalArgumentException("labId cannot be null or less than 1");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId cannot be null or less than 1");
        }
        if (batchNumber == null || batchNumber.isBlank()) {
            throw new IllegalArgumentException("batchNumber cannot be null or blank");
        }
        if (quantity == null || !Double.isFinite(quantity) || quantity <= 0) {
            throw new IllegalArgumentException("quantity cannot be null or less than or equal to 0");
        }
        if (startDate == null || startDate.isBlank()) {
            throw new IllegalArgumentException("startDate cannot be null or blank");
        }
        if (notes != null && notes.isBlank()) notes = null;
    }
}
