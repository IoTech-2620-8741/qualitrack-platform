package com.iotech.qualitrack.platform.equipment.domain.model.queries;

/**
 * Query to get the maintenance history for a specific piece of equipment.
 *
 * @param equipmentId The ID of the equipment. Cannot be null or less than 1.
 */
public record GetMaintenanceByEquipmentIdQuery(Long equipmentId) {
    public GetMaintenanceByEquipmentIdQuery {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id is required and must be greater than 0.");
        }
    }
}