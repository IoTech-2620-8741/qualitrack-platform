package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.KpiDashboardResource;

/**
 * Assembler that transforms KPI dashboard aggregate roots into REST resources.
 */
public final class KpiDashboardResourceFromEntityAssembler {
    private KpiDashboardResourceFromEntityAssembler() {
    }

    /**
     * Converts a KPI dashboard aggregate into its REST resource representation.
     *
     * @param entity the KPI dashboard aggregate
     * @return the KPI dashboard resource
     */
    public static KpiDashboardResource toResourceFromEntity(KpiDashboard entity) {
        return new KpiDashboardResource(
                entity.getId(),
                entity.getLaboratoryId(),
                entity.getTimestamp(),
                entity.getOverallHealthScore(),
                entity.getMetrics().stream()
                        .map(KpiMetricResourceFromEntityAssembler::toResourceFromEntity)
                        .toList()
        );
    }
}