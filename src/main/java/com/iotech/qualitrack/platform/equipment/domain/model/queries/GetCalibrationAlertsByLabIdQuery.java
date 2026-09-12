package com.iotech.qualitrack.platform.equipment.domain.model.queries;

/**
 * Query to get all equipment with expired or soon-to-expire calibrations for a specific laboratory.
 *
 * @param laboratoryId The ID of the laboratory. Cannot be null or less than 1.
 */
public record GetCalibrationAlertsByLabIdQuery(Long laboratoryId) {
    public GetCalibrationAlertsByLabIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id is required and must be greater than 0.");
        }
    }
}