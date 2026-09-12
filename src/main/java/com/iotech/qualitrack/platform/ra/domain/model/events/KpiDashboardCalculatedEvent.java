package com.iotech.qualitrack.platform.ra.domain.model.events;

/**
 * Event raised when a KPI dashboard snapshot is calculated.
 *
 * @param dashboardId The numeric identifier of the calculated dashboard.
 * @param laboratoryId The numeric identifier of the laboratory.
 * @param overallHealthScore The calculated overall health score.
 * @param metricCount The number of KPI metrics included in the snapshot.
 * @param calculatedAt The timestamp when the dashboard was calculated.
 */
public record KpiDashboardCalculatedEvent(
        Long dashboardId,
        Long laboratoryId,
        Double overallHealthScore,
        Integer metricCount,
        String calculatedAt
) {
    /**
     * Compact constructor for KpiDashboardCalculatedEvent.
     * Enforces Fail-Fast validation.
     */
    public KpiDashboardCalculatedEvent {
        if (dashboardId == null || dashboardId <= 0) {
            throw new IllegalArgumentException("dashboardId cannot be null or less than 1");
        }
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be null or less than 1");
        }
        if (overallHealthScore == null || overallHealthScore < 0) {
            throw new IllegalArgumentException("overallHealthScore cannot be null or negative");
        }
        if (metricCount == null || metricCount < 0) {
            throw new IllegalArgumentException("metricCount cannot be null or negative");
        }
        if (calculatedAt == null || calculatedAt.isBlank()) {
            throw new IllegalArgumentException("calculatedAt cannot be null or blank");
        }
    }
}