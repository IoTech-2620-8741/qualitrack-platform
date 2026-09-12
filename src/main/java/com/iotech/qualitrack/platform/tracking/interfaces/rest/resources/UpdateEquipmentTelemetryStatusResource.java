package com.iotech.qualitrack.platform.tracking.interfaces.rest.resources;

import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;

/**
 * Resource used to request updating equipment telemetry status.
 *
 * @param isOnline whether the equipment is online
 * @param currentStatus the current telemetry status
 * @param lastHeartbeat the latest heartbeat timestamp
 */
public record UpdateEquipmentTelemetryStatusResource(
        Boolean isOnline,
        TelemetryStatus currentStatus,
        String lastHeartbeat
) {
}