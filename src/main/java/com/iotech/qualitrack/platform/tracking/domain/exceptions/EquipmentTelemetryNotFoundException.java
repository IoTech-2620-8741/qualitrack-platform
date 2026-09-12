package com.iotech.qualitrack.platform.tracking.domain.exceptions;

/**
 * Exception thrown when an equipment telemetry aggregate cannot be found.
 */
public class EquipmentTelemetryNotFoundException extends RuntimeException {
    /**
     * Creates a new exception for a missing equipment telemetry aggregate.
     *
     * @param equipmentId the equipment identifier
     */
    public EquipmentTelemetryNotFoundException(Long equipmentId) {
        super("Equipment telemetry aggregate for equipment %d was not found".formatted(equipmentId));
    }
}