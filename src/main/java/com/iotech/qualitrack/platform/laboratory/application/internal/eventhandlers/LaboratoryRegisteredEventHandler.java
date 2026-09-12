package com.iotech.qualitrack.platform.laboratory.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.LaboratoryRegisteredEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.LaboratoryRegisteredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal LaboratoryRegisteredEvent events and publishes the corresponding integration event.
 */
@Service
@Slf4j
public class LaboratoryRegisteredEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new LaboratoryRegisteredEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public LaboratoryRegisteredEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal laboratory registered domain event.
     *
     * @param event the internal laboratory registered domain event
     */
    @EventListener(LaboratoryRegisteredEvent.class)
    public void on(LaboratoryRegisteredEvent event) {
        log.info(
                "Laboratory registered. laboratoryId={}, name={}",
                event.laboratoryId(),
                event.name()
        );

        applicationEventPublisher.publishEvent(LaboratoryRegisteredIntegrationEvent.from(event));
    }
}