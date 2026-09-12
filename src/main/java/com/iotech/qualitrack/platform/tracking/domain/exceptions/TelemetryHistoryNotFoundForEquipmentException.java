package com.iotech.qualitrack.platform.tracking.domain.exceptions;

/**
 * Exception thrown when telemetry history cannot be found for equipment.
 */
public class TelemetryHistoryNotFoundForEquipmentException extends RuntimeException {
    /**
     * Creates a new exception for missing equipment telemetry history.
     *
     * @param equipmentId the equipment identifier
     */
    public TelemetryHistoryNotFoundForEquipmentException(Long equipmentId) {
        super("No telemetry history was found for equipment %d".formatted(equipmentId));
    }
}