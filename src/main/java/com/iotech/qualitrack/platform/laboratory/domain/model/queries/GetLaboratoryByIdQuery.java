package com.iotech.qualitrack.platform.laboratory.domain.model.queries;

/**
 * Query to get laboratory by id.
 *
 * @param laboratoryId The ID of the laboratory to retrieve. Cannot be null or less than 1.
 */
public record GetLaboratoryByIdQuery(Long laboratoryId) {
    /**
     * Compact constructor for GetLaboratoryByIdQuery.
     * Validates that the laboratoryId is not null and is greater than 0.
     * @throws IllegalArgumentException if laboratoryId is null or less than 1.
     */
    public GetLaboratoryByIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id is required and must be greater than 0.");
        }
    }
}