package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.entities.KpiMetric;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.KpiMetricResource;

/**
 * Assembler that transforms KPI metric domain entities into REST resources.
 */
public final class KpiMetricResourceFromEntityAssembler {
    private KpiMetricResourceFromEntityAssembler() {
    }

    /**
     * Converts a KPI metric entity into its REST resource representation.
     *
     * @param entity the KPI metric domain entity
     * @return the KPI metric resource
     */
    public static KpiMetricResource toResourceFromEntity(KpiMetric entity) {
        return new KpiMetricResource(
                entity.getId(),
                entity.getName(),
                entity.getValue(),
                entity.getUnit(),
                entity.getTargetValue(),
                entity.getStatus(),
                entity.getRecordedAt()
        );
    }
}