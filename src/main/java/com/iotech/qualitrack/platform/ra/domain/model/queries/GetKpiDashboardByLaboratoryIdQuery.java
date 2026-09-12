package com.iotech.qualitrack.platform.ra.domain.model.queries;

/**
 * Query to get the KPI dashboard snapshot for a specific laboratory.
 *
 * @param laboratoryId The numeric identifier of the laboratory. Cannot be null or less than 1.
 */
public record GetKpiDashboardByLaboratoryIdQuery(Long laboratoryId) {
    /**
     * Compact constructor for GetKpiDashboardByLaboratoryIdQuery.
     * Validates that the laboratoryId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if laboratoryId is null or less than 1.
     */
    public GetKpiDashboardByLaboratoryIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id is required and must be greater than 0.");
        }
    }
}