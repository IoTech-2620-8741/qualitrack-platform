package com.iotech.qualitrack.platform.batch.interfaces.events;

import com.iotech.qualitrack.platform.batch.domain.model.events.BatchCreatedEvent;

/**
 * Integration event published by the Batch bounded context when a production batch is created.
 *
 * <p>This event is part of the published language of the Batch context. Other bounded contexts
 * should listen to this integration event instead of depending on internal domain events.</p>
 *
 * @param batchId the created batch identifier
 * @param laboratoryId the laboratory responsible for the batch
 * @param productId the manufactured product identifier
 * @param batchNumber the traceability code assigned to the batch
 */
public record BatchCreatedIntegrationEvent(
        Long batchId,
        Long laboratoryId,
        Long productId,
        String batchNumber
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal batch created domain event
     * @return the integration event
     */
    public static BatchCreatedIntegrationEvent from(BatchCreatedEvent event) {
        return new BatchCreatedIntegrationEvent(
                event.batchId(),
                event.laboratoryId(),
                event.productId(),
                event.batchNumber()
        );
    }
}