package com.iotech.qualitrack.platform.ra.interfaces.events;

import com.iotech.qualitrack.platform.ra.domain.model.events.KpiDashboardCalculatedEvent;

/**
 * Integration event published when a KPI dashboard has been calculated.
 *
 * <p>This event exposes KPI dashboard calculation results to other bounded contexts
 * without leaking RA internal domain events.</p>
 *
 * @param dashboardId The KPI dashboard identifier.
 * @param laboratoryId The related laboratory identifier.
 * @param overallHealthScore The calculated overall health score.
 * @param metricCount The number of metrics included in the dashboard.
 * @param calculatedAt The timestamp when the dashboard was calculated.
 */
public record KpiDashboardCalculatedIntegrationEvent(
        Long dashboardId,
        Long laboratoryId,
        Double overallHealthScore,
        Integer metricCount,
        String calculatedAt
) {

    /**
     * Creates an integration event from the internal RA domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static KpiDashboardCalculatedIntegrationEvent from(KpiDashboardCalculatedEvent event) {
        return new KpiDashboardCalculatedIntegrationEvent(
                event.dashboardId(),
                event.laboratoryId(),
                event.overallHealthScore(),
                event.metricCount(),
                event.calculatedAt()
        );
    }
}