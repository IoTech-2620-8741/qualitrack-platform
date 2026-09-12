package com.iotech.qualitrack.platform.equipment.domain.model.queries;

/**
 * Query to get all configured BPM parameter ranges (e.g., Temperature, Pressure)
 * for a specific equipment.
 *
 * @param equipmentId The ID of the equipment. Cannot be null or less than 1.
 */
public record GetBpmParameterConfigsByEquipmentIdQuery(Long equipmentId) {
    /**
     * Compact constructor for GetBpmParameterConfigsByEquipmentIdQuery.
     * Validates that the equipmentId is not null and is greater than 0.
     * * @throws IllegalArgumentException if equipmentId is null or less than 1.
     */
    public GetBpmParameterConfigsByEquipmentIdQuery {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id is required and must be greater than 0.");
        }
    }
}