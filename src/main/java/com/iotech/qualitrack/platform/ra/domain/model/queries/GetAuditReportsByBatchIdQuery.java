package com.iotech.qualitrack.platform.ra.domain.model.queries;

/**
 * Query to get generated audit reports associated with a production batch.
 *
 * @param batchId The numeric identifier of the production batch. Cannot be null or less than 1.
 */
public record GetAuditReportsByBatchIdQuery(Long batchId) {
    /**
     * Compact constructor for GetAuditReportsByBatchIdQuery.
     * Validates that the batchId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if batchId is null or less than 1.
     */
    public GetAuditReportsByBatchIdQuery {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("Batch id is required and must be greater than 0.");
        }
    }
}