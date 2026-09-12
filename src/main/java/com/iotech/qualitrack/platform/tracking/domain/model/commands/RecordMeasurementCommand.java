package com.iotech.qualitrack.platform.tracking.domain.model.commands;

/**
 * Command used to record a new telemetry measurement emitted by equipment.
 *
 * @param equipmentId the numeric identifier of the equipment that produced the measurement
 * @param parameterName the monitored parameter name
 * @param value the measured value
 * @param unit the measurement unit
 * @param timestamp the timestamp when the measurement was captured
 */
public record RecordMeasurementCommand(
        Long equipmentId,
        String parameterName,
        Double value,
        String unit,
        String timestamp
) {
    /**
     * Creates a validated command for recording telemetry measurements.
     */
    public RecordMeasurementCommand {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id must be a positive number");
        }
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("Parameter name is required");
        }
        if (value == null) {
            throw new IllegalArgumentException("Measurement value is required");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Measurement unit is required");
        }
        if (timestamp == null || timestamp.isBlank()) {
            throw new IllegalArgumentException("Measurement timestamp is required");
        }
    }
}