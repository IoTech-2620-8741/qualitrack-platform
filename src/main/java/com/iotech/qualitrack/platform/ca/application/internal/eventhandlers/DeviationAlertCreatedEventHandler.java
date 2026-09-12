package com.iotech.qualitrack.platform.ca.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ca.domain.model.events.DeviationAlertCreatedEvent;
import com.iotech.qualitrack.platform.ca.interfaces.events.DeviationAlertCreatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal DeviationAlertCreatedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class DeviationAlertCreatedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new DeviationAlertCreatedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public DeviationAlertCreatedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal deviation alert created domain event.
     *
     * @param event the internal deviation alert created domain event
     */
    @EventListener(DeviationAlertCreatedEvent.class)
    public void on(DeviationAlertCreatedEvent event) {
        log.warn(
                "Deviation alert created. alertId={}, equipmentId={}, batchId={}, parameterName={}, severity={}",
                event.alertId(),
                event.equipmentId(),
                event.batchId(),
                event.parameterName(),
                event.severity()
        );

        applicationEventPublisher.publishEvent(DeviationAlertCreatedIntegrationEvent.from(event));
    }
}