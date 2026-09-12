package com.iotech.qualitrack.platform.batch.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.batch.domain.model.events.BatchReleasedEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchReleasedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal BatchReleasedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class BatchReleasedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new BatchReleasedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public BatchReleasedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal batch released domain event.
     *
     * @param event the internal batch released domain event
     */
    @EventListener(BatchReleasedEvent.class)
    public void on(BatchReleasedEvent event) {
        log.info(
                "Batch released. batchId={}, laboratoryId={}, productId={}, batchNumber={}, releaseDate={}",
                event.batchId(),
                event.laboratoryId(),
                event.productId(),
                event.batchNumber(),
                event.releaseDate()
        );

        applicationEventPublisher.publishEvent(BatchReleasedIntegrationEvent.from(event));
    }
}