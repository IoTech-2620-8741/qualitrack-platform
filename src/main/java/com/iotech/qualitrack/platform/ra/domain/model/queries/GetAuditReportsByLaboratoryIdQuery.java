package com.iotech.qualitrack.platform.ra.domain.model.queries;

/**
 * Query to get generated audit reports for a specific laboratory.
 *
 * @param laboratoryId The numeric identifier of the laboratory. Cannot be null or less than 1.
 */
public record GetAuditReportsByLaboratoryIdQuery(Long laboratoryId) {
    /**
     * Compact constructor for GetAuditReportsByLaboratoryIdQuery.
     * Validates that the laboratoryId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if laboratoryId is null or less than 1.
     */
    public GetAuditReportsByLaboratoryIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id is required and must be greater than 0.");
        }
    }
}