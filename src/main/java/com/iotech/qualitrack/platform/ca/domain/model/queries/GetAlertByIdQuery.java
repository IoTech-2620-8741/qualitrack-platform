package com.iotech.qualitrack.platform.ca.domain.model.queries;

/**
 * Query to get a deviation alert by id.
 *
 * @param alertId The ID of the deviation alert to retrieve. Cannot be null or less than 1.
 */
public record GetAlertByIdQuery(Long alertId) {
    /**
     * Compact constructor for GetAlertByIdQuery.
     * Validates that the alertId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if alertId is null or less than 1.
     */
    public GetAlertByIdQuery {
        if (alertId == null || alertId <= 0) {
            throw new IllegalArgumentException("Alert id is required and must be greater than 0.");
        }
    }
}