package com.iotech.qualitrack.platform.tracking.domain.model.events;

/**
 * Event published when a telemetry measurement is recorded.
 *
 * @param measurementId the recorded measurement identifier
 * @param equipmentId the equipment that produced the measurement
 * @param parameterName the monitored parameter name
 * @param value the measured value
 * @param unit the measurement unit
 * @param timestamp the source timestamp of the measurement
 */
public record MeasurementRecordedEvent(
        Long measurementId,
        Long equipmentId,
        String parameterName,
        Double value,
        String unit,
        String timestamp
) {
    /**
     * Creates a validated measurement recorded event.
     */
    public MeasurementRecordedEvent {
        if (measurementId == null || measurementId <= 0) {
            throw new IllegalArgumentException("Measurement id must be a positive number");
        }
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