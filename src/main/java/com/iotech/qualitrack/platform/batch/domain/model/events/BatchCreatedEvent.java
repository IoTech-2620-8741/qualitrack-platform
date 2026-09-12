package com.iotech.qualitrack.platform.batch.domain.model.events;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;

/**
 * Domain event published when a new {@link Batch} is successfully created and persisted.
 *
 * <p>Other bounded contexts, such as reporting or compliance, can listen to this event
 * to react to batch creation without directly coupling to the {@code batch}
 * application services.</p>
 *
 * @param batchId     The numeric identity assigned to the newly created batch.
 * @param laboratoryId The numeric identity of the laboratory responsible for the batch.
 * @param productId   The numeric identity of the manufactured product.
 * @param batchNumber The traceability code assigned to the batch.
 */
public record BatchCreatedEvent(
        Long batchId,
        Long laboratoryId,
        Long productId,
        String batchNumber) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link Batch}.
     *
     * @param batch the saved batch
     * @return a fully populated {@link BatchCreatedEvent}
     */
    public static BatchCreatedEvent from(Batch batch) {
        return new BatchCreatedEvent(
                batch.getId(),
                batch.getLabId(),
                batch.getProductId(),
                batch.getBatchNumber()
        );
    }
}