package com.iotech.qualitrack.platform.equipment.interfaces.events;

import com.iotech.qualitrack.platform.equipment.domain.model.events.SensorLinkedEvent;

/**
 * Integration event published by the Equipment bounded context when an external sensor is linked to equipment.
 *
 * <p>This event allows telemetry ingestion and tracking workflows to start accepting
 * measurements for the linked sensor without depending on Equipment internal domain events.</p>
 *
 * @param equipmentId the equipment identifier
 * @param sensorExternalId the external IoT sensor identifier
 */
public record SensorLinkedIntegrationEvent(
        Long equipmentId,
        String sensorExternalId
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal sensor linked domain event
     * @return the integration event
     */
    public static SensorLinkedIntegrationEvent from(SensorLinkedEvent event) {
        return new SensorLinkedIntegrationEvent(
                event.equipmentId(),
                event.sensorExternalId()
        );
    }
}