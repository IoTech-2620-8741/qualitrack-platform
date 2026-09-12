package com.iotech.qualitrack.platform.ra.domain.model.queries;

/**
 * Query to get audit log entries using optional filters.
 *
 * @param equipmentId The numeric identifier of the equipment, if filtering by equipment.
 * @param batchId The numeric identifier of the production batch, if filtering by batch.
 * @param dateFrom The lower timestamp bound, if filtering by date range.
 * @param dateTo The upper timestamp bound, if filtering by date range.
 */
public record GetAuditLogQuery(
        Long equipmentId,
        Long batchId,
        String dateFrom,
        String dateTo
) {
    /**
     * Compact constructor for GetAuditLogQuery.
     * Enforces Fail-Fast validation for provided filters.
     */
    public GetAuditLogQuery {
        if (equipmentId != null && equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId must be greater than 0.");
        }
        if (batchId != null && batchId <= 0) {
            throw new IllegalArgumentException("batchId must be greater than 0.");
        }
        if (dateFrom != null && dateFrom.isBlank()) {
            throw new IllegalArgumentException("dateFrom cannot be blank.");
        }
        if (dateTo != null && dateTo.isBlank()) {
            throw new IllegalArgumentException("dateTo cannot be blank.");
        }
    }
}