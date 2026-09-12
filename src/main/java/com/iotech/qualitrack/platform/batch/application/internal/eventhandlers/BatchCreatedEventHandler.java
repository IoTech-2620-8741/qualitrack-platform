package com.iotech.qualitrack.platform.batch.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.batch.domain.model.events.BatchCreatedEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchCreatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal BatchCreatedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class BatchCreatedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new BatchCreatedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public BatchCreatedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal batch created domain event.
     *
     * @param event the internal batch created domain event
     */
    @EventListener(BatchCreatedEvent.class)
    public void on(BatchCreatedEvent event) {
        log.info(
                "Batch created. batchId={}, laboratoryId={}, productId={}, batchNumber={}",
                event.batchId(),
                event.laboratoryId(),
                event.productId(),
                event.batchNumber()
        );

        applicationEventPublisher.publishEvent(BatchCreatedIntegrationEvent.from(event));
    }
}