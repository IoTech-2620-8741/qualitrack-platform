package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import java.util.List;

/**
 * Resource representation of a KPI dashboard snapshot.
 *
 * @param id The unique numeric identifier of the dashboard snapshot.
 * @param laboratoryId The numeric identifier of the laboratory.
 * @param timestamp The timestamp when this dashboard was generated.
 * @param overallHealthScore The calculated overall health score.
 * @param metrics The KPI metrics included in the dashboard.
 */
public record KpiDashboardResource(
        Long id,
        Long laboratoryId,
        String timestamp,
        Double overallHealthScore,
        List<KpiMetricResource> metrics
) {
}