package com.iotech.qualitrack.platform.batch.domain.model.queries;

/**
 * Query to get batch by id.
 *
 * @param batchId The ID of the batch to retrieve. Cannot be null or less than 1.
 */
public record GetBatchByIdQuery(Long batchId) {
    /**
     * Compact constructor for GetBatchByIdQuery.
     * Validates that the batchId is not null and is greater than 0.
     * @throws IllegalArgumentException if batchId is null or less than 1.
     */
    public GetBatchByIdQuery {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("Batch id is required and must be greater than 0.");
        }
    }
}