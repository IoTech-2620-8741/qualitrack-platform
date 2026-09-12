package com.iotech.qualitrack.platform.ca.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ca.domain.model.events.DeviationAlertResolvedEvent;
import com.iotech.qualitrack.platform.ca.interfaces.events.DeviationAlertResolvedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal DeviationAlertResolvedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class DeviationAlertResolvedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new DeviationAlertResolvedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public DeviationAlertResolvedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal deviation alert resolved domain event.
     *
     * @param event the internal deviation alert resolved domain event
     */
    @EventListener(DeviationAlertResolvedEvent.class)
    public void on(DeviationAlertResolvedEvent event) {
        log.info(
                "Deviation alert resolved. alertId={}, equipmentId={}, batchId={}, resolvedBy={}",
                event.alertId(),
                event.equipmentId(),
                event.batchId(),
                event.resolvedBy()
        );

        applicationEventPublisher.publishEvent(DeviationAlertResolvedIntegrationEvent.from(event));
    }
}