package com.iotech.qualitrack.platform.tracking.domain.exceptions;

/**
 * Exception thrown when no telemetry measurements are found for equipment.
 */
public class MeasurementsNotFoundForEquipmentException extends RuntimeException {
    /**
     * Creates a new exception for missing equipment measurements.
     *
     * @param equipmentId the equipment identifier
     */
    public MeasurementsNotFoundForEquipmentException(Long equipmentId) {
        super("No telemetry measurements were found for equipment %d".formatted(equipmentId));
    }
}