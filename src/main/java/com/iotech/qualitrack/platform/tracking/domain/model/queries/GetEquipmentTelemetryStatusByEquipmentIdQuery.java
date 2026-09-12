package com.iotech.qualitrack.platform.tracking.domain.model.queries;

/**
 * Query used to retrieve the current telemetry status of an equipment.
 *
 * @param equipmentId the numeric identifier of the equipment
 */
public record GetEquipmentTelemetryStatusByEquipmentIdQuery(
        Long equipmentId
) {
    /**
     * Creates a validated query for retrieving equipment telemetry status.
     */
    public GetEquipmentTelemetryStatusByEquipmentIdQuery {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id must be a positive number");
        }
    }
}