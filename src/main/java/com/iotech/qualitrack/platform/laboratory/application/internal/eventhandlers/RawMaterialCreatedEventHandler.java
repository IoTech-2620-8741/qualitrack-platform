package com.iotech.qualitrack.platform.laboratory.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialCreatedEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.RawMaterialCreatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles raw material creation domain events and publishes Laboratory integration events.
 */
@Service
@Slf4j
public class RawMaterialCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public RawMaterialCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles raw material creation events.
     *
     * @param event The internal Laboratory domain event.
     */
    @EventListener(RawMaterialCreatedEvent.class)
    public void on(RawMaterialCreatedEvent event) {
        log.info(
                "Raw material created: Raw Material ID='{}', Laboratory ID='{}', Code='{}', Name='{}', Current Stock='{}', Unit='{}'.",
                event.rawMaterialId(),
                event.laboratoryId(),
                event.code(),
                event.name(),
                event.currentStock(),
                event.unit()
        );

        eventPublisher.publishEvent(RawMaterialCreatedIntegrationEvent.from(event));
    }
}