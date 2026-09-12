package com.iotech.qualitrack.platform.laboratory.domain.model.queries;

/**
 * Query to get all raw materials by laboratory id.
 *
 * @param laboratoryId The ID of the laboratory. Cannot be null or less than 1.
 */
public record GetRawMaterialsByLabIdQuery(Long laboratoryId) {
    public GetRawMaterialsByLabIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id is required and must be greater than 0.");
        }
    }
}