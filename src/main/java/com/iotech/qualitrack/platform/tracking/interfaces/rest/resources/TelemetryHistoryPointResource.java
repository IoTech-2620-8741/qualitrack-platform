package com.iotech.qualitrack.platform.tracking.interfaces.rest.resources;

/**
 * Resource representation of a historical telemetry point exposed through REST.
 *
 * @param id the history point identifier
 * @param equipmentId the equipment that produced the telemetry point
 * @param parameterName the monitored parameter name
 * @param recordedValue the recorded telemetry value
 * @param timestamp the source timestamp
 * @param isAnomaly whether this point was detected as an anomaly
 * @param createdAt the persistence creation timestamp
 */
public record TelemetryHistoryPointResource(
        Long id,
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String timestamp,
        Boolean isAnomaly,
        String createdAt
) {
}