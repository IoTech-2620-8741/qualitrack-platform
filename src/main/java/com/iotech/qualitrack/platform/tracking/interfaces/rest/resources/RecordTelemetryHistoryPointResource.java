package com.iotech.qualitrack.platform.tracking.interfaces.rest.resources;

/**
 * Resource used to request recording a telemetry history point.
 *
 * @param parameterName the monitored parameter name
 * @param recordedValue the recorded telemetry value
 * @param timestamp the source timestamp
 * @param isAnomaly whether this point was detected as an anomaly
 */
public record RecordTelemetryHistoryPointResource(
        String parameterName,
        Double recordedValue,
        String timestamp,
        Boolean isAnomaly
) {
}