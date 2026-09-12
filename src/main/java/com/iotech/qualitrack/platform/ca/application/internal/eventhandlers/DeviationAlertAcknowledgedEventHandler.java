package com.iotech.qualitrack.platform.ca.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ca.domain.model.events.DeviationAlertAcknowledgedEvent;
import com.iotech.qualitrack.platform.ca.interfaces.events.DeviationAlertAcknowledgedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal DeviationAlertAcknowledgedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class DeviationAlertAcknowledgedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new DeviationAlertAcknowledgedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public DeviationAlertAcknowledgedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal deviation alert acknowledged domain event.
     *
     * @param event the internal deviation alert acknowledged domain event
     */
    @EventListener(DeviationAlertAcknowledgedEvent.class)
    public void on(DeviationAlertAcknowledgedEvent event) {
        log.info(
                "Deviation alert acknowledged. alertId={}, equipmentId={}, batchId={}, acknowledgedBy={}",
                event.alertId(),
                event.equipmentId(),
                event.batchId(),
                event.acknowledgedBy()
        );

        applicationEventPublisher.publishEvent(DeviationAlertAcknowledgedIntegrationEvent.from(event));
    }
}