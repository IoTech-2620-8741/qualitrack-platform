package com.iotech.qualitrack.platform.tracking.interfaces.rest.resources;

import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;

/**
 * Resource representation of the current telemetry status of equipment.
 *
 * @param id the telemetry status identifier
 * @param equipmentId the monitored equipment identifier
 * @param isOnline whether the equipment is online
 * @param currentStatus the current telemetry health status
 * @param lastHeartbeat the latest heartbeat timestamp
 * @param createdAt the persistence creation timestamp
 */
public record EquipmentTelemetryStatusResource(
        Long id,
        Long equipmentId,
        Boolean isOnline,
        TelemetryStatus currentStatus,
        String lastHeartbeat,
        String createdAt
) {
}