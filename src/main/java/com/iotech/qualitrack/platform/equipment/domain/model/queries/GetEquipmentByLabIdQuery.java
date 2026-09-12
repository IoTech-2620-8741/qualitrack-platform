package com.iotech.qualitrack.platform.equipment.domain.model.queries;

/**
 * Query to get all equipment belonging to a specific laboratory.
 *
 * @param laboratoryId The ID of the laboratory. Cannot be null or less than 1.
 */
public record GetEquipmentByLabIdQuery(Long laboratoryId) {
    public GetEquipmentByLabIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id is required and must be greater than 0.");
        }
    }
}