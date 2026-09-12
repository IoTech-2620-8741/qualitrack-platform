package com.iotech.qualitrack.platform.ra.domain.model.queries;

/**
 * Query to get deviation trend analyses for a specific equipment.
 *
 * @param equipmentId The numeric identifier of the equipment. Cannot be null or less than 1.
 */
public record GetDeviationTrendsByEquipmentIdQuery(Long equipmentId) {
    /**
     * Compact constructor for GetDeviationTrendsByEquipmentIdQuery.
     * Validates that the equipmentId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if equipmentId is null or less than 1.
     */
    public GetDeviationTrendsByEquipmentIdQuery {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id is required and must be greater than 0.");
        }
    }
}