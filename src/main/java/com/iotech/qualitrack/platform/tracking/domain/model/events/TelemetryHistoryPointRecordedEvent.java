package com.iotech.qualitrack.platform.tracking.domain.model.events;

/**
 * Event published when a telemetry history point is recorded.
 *
 * @param historyPointId the recorded history point identifier
 * @param equipmentId the equipment that produced the telemetry point
 * @param parameterName the monitored parameter name
 * @param recordedValue the recorded telemetry value
 * @param timestamp the source timestamp of the telemetry point
 * @param isAnomaly whether the point was detected as an anomaly
 */
public record TelemetryHistoryPointRecordedEvent(
        Long historyPointId,
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String timestamp,
        Boolean isAnomaly
) {
    /**
     * Creates a validated telemetry history point recorded event.
     */
    public TelemetryHistoryPointRecordedEvent {
        if (historyPointId == null || historyPointId <= 0) {
            throw new IllegalArgumentException("History point id must be a positive number");
        }
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