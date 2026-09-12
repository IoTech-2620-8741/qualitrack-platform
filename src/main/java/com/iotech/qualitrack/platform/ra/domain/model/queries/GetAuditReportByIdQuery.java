package com.iotech.qualitrack.platform.ra.domain.model.queries;

/**
 * Query to get a generated audit report by its unique identifier.
 *
 * @param reportId The numeric identifier of the generated report. Cannot be null or less than 1.
 */
public record GetAuditReportByIdQuery(Long reportId) {
    /**
     * Compact constructor for GetAuditReportByIdQuery.
     * Validates that the reportId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if reportId is null or less than 1.
     */
    public GetAuditReportByIdQuery {
        if (reportId == null || reportId <= 0) {
            throw new IllegalArgumentException("Report id is required and must be greater than 0.");
        }
    }
}