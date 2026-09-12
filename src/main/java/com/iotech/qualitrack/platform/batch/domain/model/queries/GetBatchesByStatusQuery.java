package com.iotech.qualitrack.platform.batch.domain.model.queries;

import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;

/**
 * Query to get all production batches by status.
 *
 * @param status The lifecycle status of the batch. Cannot be null.
 */
public record GetBatchesByStatusQuery(BatchStatus status) {
    public GetBatchesByStatusQuery {
        if (status == null) {
            throw new IllegalArgumentException("Batch status is required.");
        }
    }
}