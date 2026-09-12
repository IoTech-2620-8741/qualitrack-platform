package com.iotech.qualitrack.platform.batch.domain.model.queries;

/**
 * Query to get all raw material usage records belonging to a specific batch.
 *
 * @param batchId The ID of the batch. Cannot be null or less than 1.
 */
public record GetRawMaterialUsageByBatchIdQuery(Long batchId) {
    public GetRawMaterialUsageByBatchIdQuery {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("Batch id is required and must be greater than 0.");
        }
    }
}