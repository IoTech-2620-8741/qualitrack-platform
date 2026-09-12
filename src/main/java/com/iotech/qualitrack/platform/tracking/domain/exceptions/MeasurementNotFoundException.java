package com.iotech.qualitrack.platform.tracking.domain.exceptions;

/**
 * Exception thrown when a telemetry measurement cannot be found.
 */
public class MeasurementNotFoundException extends RuntimeException {
    /**
     * Creates a new exception for a missing measurement.
     *
     * @param measurementId the missing measurement identifier
     */
    public MeasurementNotFoundException(Long measurementId) {
        super("Measurement with id %d was not found".formatted(measurementId));
    }
}