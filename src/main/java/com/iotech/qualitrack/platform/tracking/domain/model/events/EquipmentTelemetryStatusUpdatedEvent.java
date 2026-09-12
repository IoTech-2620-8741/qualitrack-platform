package com.iotech.qualitrack.platform.tracking.domain.model.events;

import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;

/**
 * Event published when the current telemetry status of equipment changes.
 *
 * @param statusId the telemetry status record identifier
 * @param equipmentId the monitored equipment identifier
 * @param isOnline whether the equipment is online
 * @param currentStatus the current telemetry health status
 * @param lastHeartbeat the latest heartbeat timestamp
 */
public record EquipmentTelemetryStatusUpdatedEvent(
        Long statusId,
        Long equipmentId,
        Boolean isOnline,
        TelemetryStatus currentStatus,
        String lastHeartbeat
) {
    /**
     * Creates a validated equipment telemetry status updated event.
     */
    public EquipmentTelemetryStatusUpdatedEvent {
        if (statusId == null || statusId <= 0) {
            throw new IllegalArgumentException("Status id must be a positive number");
        }
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id must be a positive number");
        }
        if (isOnline == null) {
            throw new IllegalArgumentException("Online status is required");
        }
        if (currentStatus == null) {
            throw new IllegalArgumentException("Telemetry status is required");
        }
        if (lastHeartbeat == null || lastHeartbeat.isBlank()) {
            throw new IllegalArgumentException("Last heartbeat timestamp is required");
        }
    }
}