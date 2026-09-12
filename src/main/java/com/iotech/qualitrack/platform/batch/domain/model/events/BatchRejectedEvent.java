package com.iotech.qualitrack.platform.batch.domain.model.events;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;

/**
 * Domain event published when a production batch is rejected.
 *
 * <p>Can be listened to by reporting, audit, or compliance contexts to record
 * the failed quality control outcome of a batch.</p>
 *
 * @param batchId       The numeric identity of the rejected batch.
 * @param laboratoryId  The numeric identity of the laboratory responsible for the batch.
 * @param productId     The numeric identity of the manufactured product.
 * @param batchNumber   The traceability code assigned to the batch.
 * @param rejectionDate The date when the batch was rejected.
 * @param reason        The rejection justification.
 */
public record BatchRejectedEvent(
        Long batchId,
        Long laboratoryId,
        Long productId,
        String batchNumber,
        String rejectionDate,
        String reason) {

    /**
     * Convenience factory that extracts all needed fields from a rejected {@link Batch}.
     *
     * @param batch the rejected batch
     * @return a fully populated {@link BatchRejectedEvent}
     */
    public static BatchRejectedEvent from(Batch batch) {
        return new BatchRejectedEvent(
                batch.getId(),
                batch.getLabId(),
                batch.getProductId(),
                batch.getBatchNumber(),
                batch.getEndDate(),
                batch.getNotes()
        );
    }
}