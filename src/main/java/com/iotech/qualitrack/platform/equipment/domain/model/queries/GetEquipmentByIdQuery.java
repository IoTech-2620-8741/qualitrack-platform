package com.iotech.qualitrack.platform.equipment.domain.model.queries;

/**
 * Query to get equipment by id.
 *
 * @param equipmentId The ID of the equipment to retrieve. Cannot be null or less than 1.
 */
public record GetEquipmentByIdQuery(Long equipmentId) {
    /**
     * Compact constructor for GetEquipmentByIdQuery.
     * Validates that the equipmentId is not null and is greater than 0.
     * @throws IllegalArgumentException if equipmentId is null or less than 1.
     */
    public GetEquipmentByIdQuery {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id is required and must be greater than 0.");
        }
    }
}