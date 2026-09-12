package com.iotech.qualitrack.platform.ca.domain.model.queries;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;

/**
 * Query to get deviation alerts using optional filters.
 *
 * @param equipmentId The numeric identifier of the equipment, if filtering by equipment.
 * @param batchId The numeric identifier of the batch, if filtering by batch.
 * @param status The lifecycle status of the alert, if filtering by status.
 * @param severity The impact level of the alert, if filtering by severity.
 */
public record GetAlertsQuery(
        Long equipmentId,
        Long batchId,
        AlertStatus status,
        AlertSeverity severity
) {
    /**
     * Compact constructor for GetAlertsQuery.
     * Enforces Fail-Fast validation for provided filters.
     */
    public GetAlertsQuery {
        if (equipmentId != null && equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId must be greater than 0.");
        }
        if (batchId != null && batchId <= 0) {
            throw new IllegalArgumentException("batchId must be greater than 0.");
        }
    }
}