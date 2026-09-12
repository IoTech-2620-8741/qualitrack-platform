package com.iotech.qualitrack.platform.tracking.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.tracking.domain.model.events.EquipmentTelemetryStatusUpdatedEvent;
import com.iotech.qualitrack.platform.tracking.interfaces.events.EquipmentTelemetryStatusUpdatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal equipment telemetry status updated events and publishes Tracking integration events.
 */
@Service
@Slf4j
public class EquipmentTelemetryStatusUpdatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public EquipmentTelemetryStatusUpdatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles an equipment telemetry status updated domain event.
     *
     * @param event the internal Tracking domain event
     */
    @EventListener(EquipmentTelemetryStatusUpdatedEvent.class)
    public void on(EquipmentTelemetryStatusUpdatedEvent event) {
        log.info(
                "Equipment telemetry status updated: Status ID='{}', Equipment ID='{}', IsOnline='{}', CurrentStatus='{}', LastHeartbeat='{}'.",
                event.statusId(),
                event.equipmentId(),
                event.isOnline(),
                event.currentStatus(),
                event.lastHeartbeat()
        );

        eventPublisher.publishEvent(EquipmentTelemetryStatusUpdatedIntegrationEvent.from(event));
    }
}