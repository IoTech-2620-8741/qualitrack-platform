package com.iotech.qualitrack.platform.ra.domain.model.queries;

/**
 * Query to get generated audit reports associated with an equipment.
 *
 * @param equipmentId The numeric identifier of the equipment. Cannot be null or less than 1.
 */
public record GetAuditReportsByEquipmentIdQuery(Long equipmentId) {
    /**
     * Compact constructor for GetAuditReportsByEquipmentIdQuery.
     * Validates that the equipmentId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if equipmentId is null or less than 1.
     */
    public GetAuditReportsByEquipmentIdQuery {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id is required and must be greater than 0.");
        }
    }
}