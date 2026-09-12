package com.iotech.qualitrack.platform.tracking.domain.model.commands;

/**
 * Command used to persist a historical telemetry point.
 *
 * @param equipmentId the numeric identifier of the equipment
 * @param parameterName the monitored parameter name
 * @param recordedValue the value recorded for the telemetry point
 * @param timestamp the timestamp when the telemetry point was captured
 * @param isAnomaly whether the point was detected as an anomaly
 */
public record RecordTelemetryHistoryPointCommand(
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String timestamp,
        Boolean isAnomaly
) {
    /**
     * Creates a validated command for recording telemetry history points.
     */
    public RecordTelemetryHistoryPointCommand {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id must be a positive number");
        }
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("Parameter name is required");
        }
        if (recordedValue == null) {
            throw new IllegalArgumentException("Recorded value is required");
        }
        if (timestamp == null || timestamp.isBlank()) {
            throw new IllegalArgumentException("Telemetry timestamp is required");
        }
        if (isAnomaly == null) {
            throw new IllegalArgumentException("Anomaly flag is required");
        }
    }
}