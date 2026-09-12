package com.iotech.qualitrack.platform.equipment.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.equipment.domain.model.events.MaintenanceRegisteredEvent;
import com.iotech.qualitrack.platform.equipment.interfaces.events.MaintenanceRegisteredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles maintenance registration domain events and publishes Equipment integration events.
 */
@Service
@Slf4j
public class MaintenanceRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public MaintenanceRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles maintenance registration events.
     *
     * @param event The internal Equipment domain event.
     */
    @EventListener(MaintenanceRegisteredEvent.class)
    public void on(MaintenanceRegisteredEvent event) {
        log.info(
                "Maintenance registered: Record ID='{}', Equipment ID='{}', Date='{}', Type='{}', Technician='{}'.",
                event.maintenanceRecordId(),
                event.equipmentId(),
                event.maintenanceDate(),
                event.type(),
                event.technicianName()
        );

        eventPublisher.publishEvent(MaintenanceRegisteredIntegrationEvent.from(event));
    }
}