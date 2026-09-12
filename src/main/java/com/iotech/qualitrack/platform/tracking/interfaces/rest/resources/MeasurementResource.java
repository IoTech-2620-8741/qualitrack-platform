package com.iotech.qualitrack.platform.tracking.interfaces.rest.resources;

/**
 * Resource representation of a telemetry measurement exposed through REST.
 *
 * @param id the measurement identifier
 * @param equipmentId the equipment that produced the measurement
 * @param parameterName the monitored parameter name
 * @param value the measured value
 * @param unit the measurement unit
 * @param timestamp the source timestamp
 * @param createdAt the persistence creation timestamp
 */
public record MeasurementResource(
        Long id,
        Long equipmentId,
        String parameterName,
        Double value,
        String unit,
        String timestamp,
        String createdAt
) {
}