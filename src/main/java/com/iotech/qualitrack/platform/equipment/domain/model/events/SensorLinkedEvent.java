package com.iotech.qualitrack.platform.equipment.domain.model.events;

/**
 * Domain event published when an external IoT sensor is successfully linked to an equipment.
 *
 * <p>Can be listened to by the telemetry ingestion engine to start accepting and mapping
 * data from this specific sensor ID to the corresponding equipment.</p>
 *
 * @param equipmentId      The numeric identity of the equipment.
 * @param sensorExternalId The external identifier of the newly linked IoT sensor.
 */
public record SensorLinkedEvent(
        Long equipmentId,
        String sensorExternalId) {
}