package com.iotech.qualitrack.platform.tracking.domain.model.commands;

import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;

/**
 * Command used to update the current telemetry status of equipment.
 *
 * @param equipmentId the numeric identifier of the equipment
 * @param isOnline whether the equipment is currently communicating
 * @param currentStatus the evaluated telemetry health status
 * @param lastHeartbeat the timestamp of the latest received heartbeat
 */
public record UpdateEquipmentTelemetryStatusCommand(
        Long equipmentId,
        Boolean isOnline,
        TelemetryStatus currentStatus,
        String lastHeartbeat
) {
    /**
     * Creates a validated command for updating equipment telemetry status.
     */
    public UpdateEquipmentTelemetryStatusCommand {
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