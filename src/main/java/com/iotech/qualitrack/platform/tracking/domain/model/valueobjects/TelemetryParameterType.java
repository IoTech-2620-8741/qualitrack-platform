package com.iotech.qualitrack.platform.tracking.domain.model.valueobjects;

/**
 * Defines common telemetry parameter categories monitored by the Tracking
 * bounded context.
 *
 * @remarks
 * This value object is intentionally broad and can be extended as more
 * pharmaceutical equipment sensors are integrated into the platform.
 */
public enum TelemetryParameterType {
    /**
     * Temperature-related readings.
     */
    TEMPERATURE,

    /**
     * Humidity-related readings.
     */
    HUMIDITY,

    /**
     * Pressure-related readings.
     */
    PRESSURE,

    /**
     * Rotational speed or revolutions per minute readings.
     */
    RPM,

    /**
     * Voltage or electrical signal readings.
     */
    VOLTAGE,

    /**
     * Generic parameter used when the telemetry source does not map to a known type.
     */
    CUSTOM
}