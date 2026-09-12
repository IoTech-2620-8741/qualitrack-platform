package com.iotech.qualitrack.platform.tracking.domain.model.valueobjects;

/**
 * Represents the evaluated telemetry health state of an equipment.
 *
 * @remarks
 * This status is calculated from incoming telemetry measurements, anomaly
 * detection rules, and heartbeat availability. It is exposed to the frontend
 * as the current operational state of monitored equipment.
 */
public enum TelemetryStatus {
    /**
     * Equipment is online and all monitored telemetry values are within
     * acceptable operational ranges.
     */
    OPERATIONAL,

    /**
     * Equipment is online, but one or more telemetry values are approaching
     * risk thresholds or require attention.
     */
    WARNING,

    /**
     * Equipment is online, but one or more telemetry values have crossed
     * critical thresholds or represent an anomaly.
     */
    CRITICAL,

    /**
     * Equipment is not currently communicating or has no recent heartbeat.
     */
    OFFLINE
}