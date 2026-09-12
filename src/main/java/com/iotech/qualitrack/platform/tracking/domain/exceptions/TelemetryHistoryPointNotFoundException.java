package com.iotech.qualitrack.platform.tracking.domain.exceptions;

/**
 * Exception thrown when a telemetry history point cannot be found.
 */
public class TelemetryHistoryPointNotFoundException extends RuntimeException {
    /**
     * Creates a new exception for a missing telemetry history point.
     *
     * @param historyPointId the missing history point identifier
     */
    public TelemetryHistoryPointNotFoundException(Long historyPointId) {
        super("Telemetry history point with id %d was not found".formatted(historyPointId));
    }
}