package com.iotech.qualitrack.platform.tracking.domain.model.events;

/**
 * Event published when telemetry data is classified as anomalous.
 *
 * @param historyPointId the anomalous history point identifier
 * @param equipmentId the monitored equipment identifier
 * @param parameterName the monitored parameter name
 * @param recordedValue the recorded telemetry value
 * @param timestamp the source timestamp of the anomalous point
 */
public record TelemetryAnomalyDetectedEvent(
        Long historyPointId,
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String timestamp
) {
    /**
     * Creates a validated telemetry anomaly detected event.
     */
    public TelemetryAnomalyDetectedEvent {
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
    }
}