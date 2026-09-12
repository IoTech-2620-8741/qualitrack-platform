package com.iotech.qualitrack.platform.batch.domain.model.queries;

/**
 * Query to get all production batches by laboratory id.
 *
 * @param labId The ID of the laboratory. Cannot be null or less than 1.
 */
public record GetBatchesByLabIdQuery(Long labId) {
    /**
     * Compact constructor for GetBatchesByLabIdQuery.
     * Validates that the labId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if labId is null or less than 1.
     */
    public GetBatchesByLabIdQuery {
        if (labId == null || labId <= 0) {
            throw new IllegalArgumentException("Laboratory id is required and must be greater than 0.");
        }
    }
}