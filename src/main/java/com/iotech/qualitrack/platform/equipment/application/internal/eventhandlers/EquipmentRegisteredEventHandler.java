package com.iotech.qualitrack.platform.equipment.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.equipment.domain.model.events.EquipmentRegisteredEvent;
import com.iotech.qualitrack.platform.equipment.interfaces.events.EquipmentRegisteredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal EquipmentRegisteredEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class EquipmentRegisteredEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new EquipmentRegisteredEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public EquipmentRegisteredEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal equipment registered domain event.
     *
     * @param event the internal equipment registered domain event
     */
    @EventListener(EquipmentRegisteredEvent.class)
    public void on(EquipmentRegisteredEvent event) {
        log.info(
                "Equipment registered. equipmentId={}, laboratoryId={}, name={}, serialNumber={}",
                event.equipmentId(),
                event.laboratoryId(),
                event.name(),
                event.serialNumber()
        );

        applicationEventPublisher.publishEvent(EquipmentRegisteredIntegrationEvent.from(event));
    }
}