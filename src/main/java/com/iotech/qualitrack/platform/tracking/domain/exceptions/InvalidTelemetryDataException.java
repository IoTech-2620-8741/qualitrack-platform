package com.iotech.qualitrack.platform.tracking.domain.exceptions;

/**
 * Exception thrown when a telemetry command references invalid or inconsistent data.
 */
public class InvalidTelemetryDataException extends RuntimeException {
    /**
     * Creates a new invalid telemetry data exception.
     *
     * @param message the validation or consistency error message
     */
    public InvalidTelemetryDataException(String message) {
        super(message);
    }
}