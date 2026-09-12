package com.iotech.qualitrack.platform.equipment.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.equipment.domain.model.events.SensorLinkedEvent;
import com.iotech.qualitrack.platform.equipment.interfaces.events.SensorLinkedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal SensorLinkedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class SensorLinkedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new SensorLinkedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public SensorLinkedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal sensor linked domain event.
     *
     * @param event the internal sensor linked domain event
     */
    @EventListener(SensorLinkedEvent.class)
    public void on(SensorLinkedEvent event) {
        log.info(
                "Sensor linked to equipment. equipmentId={}, sensorExternalId={}",
                event.equipmentId(),
                event.sensorExternalId()
        );

        applicationEventPublisher.publishEvent(SensorLinkedIntegrationEvent.from(event));
    }
}