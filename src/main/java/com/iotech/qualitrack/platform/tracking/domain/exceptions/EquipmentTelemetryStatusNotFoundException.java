package com.iotech.qualitrack.platform.tracking.domain.exceptions;

/**
 * Exception thrown when an equipment telemetry status cannot be found.
 */
public class EquipmentTelemetryStatusNotFoundException extends RuntimeException {
    /**
     * Creates a new exception for a missing telemetry status.
     *
     * @param equipmentId the equipment identifier
     */
    public EquipmentTelemetryStatusNotFoundException(Long equipmentId) {
        super("Telemetry status for equipment %d was not found".formatted(equipmentId));
    }
}