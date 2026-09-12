package com.iotech.qualitrack.platform.batch.domain.model.commands;

/**
 * Command to request the rejection of a production batch.
 *
 * @param batchId The numeric identifier of the batch. Cannot be null or less than 1.
 * @param rejectionDate The date the batch was rejected. Cannot be null or blank.
 * @param reason The justification for rejecting the batch. Cannot be null or blank.
 */
public record RejectBatchCommand(
        Long batchId,
        String rejectionDate,
        String reason
) {
    /**
     * Compact constructor for RejectBatchCommand.
     * Enforces Fail-Fast validation.
     */
    public RejectBatchCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("batchId cannot be null or less than 1");
        }
        if (rejectionDate == null || rejectionDate.isBlank()) {
            throw new IllegalArgumentException("rejectionDate cannot be null or blank");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason cannot be null or blank");
        }
    }
}