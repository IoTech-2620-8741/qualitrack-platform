package com.iotech.qualitrack.platform.batch.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.batch.domain.model.events.BatchRejectedEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchRejectedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal BatchRejectedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class BatchRejectedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new BatchRejectedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public BatchRejectedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal batch rejected domain event.
     *
     * @param event the internal batch rejected domain event
     */
    @EventListener(BatchRejectedEvent.class)
    public void on(BatchRejectedEvent event) {
        log.info(
                "Batch rejected. batchId={}, laboratoryId={}, productId={}, batchNumber={}, rejectionDate={}",
                event.batchId(),
                event.laboratoryId(),
                event.productId(),
                event.batchNumber(),
                event.rejectionDate()
        );

        applicationEventPublisher.publishEvent(BatchRejectedIntegrationEvent.from(event));
    }
}