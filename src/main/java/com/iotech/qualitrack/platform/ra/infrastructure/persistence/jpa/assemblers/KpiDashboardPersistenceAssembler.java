package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.domain.model.entities.KpiMetric;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.KpiDashboardPersistenceEntity;

import java.util.List;

/**
 * Static assembler between KPI dashboard domain and persistence representations.
 */
public final class KpiDashboardPersistenceAssembler {

    private KpiDashboardPersistenceAssembler() {
    }

    public static KpiDashboard toDomainFromPersistence(
            KpiDashboardPersistenceEntity entity,
            List<KpiMetric> metrics
    ) {
        if (entity == null) return null;

        return new KpiDashboard(
                entity.getId(),
                entity.getLaboratoryId(),
                entity.getTimestamp(),
                entity.getOverallHealthScore(),
                metrics
        );
    }

    public static KpiDashboardPersistenceEntity toPersistenceFromDomain(KpiDashboard dashboard) {
        if (dashboard == null) return null;

        var entity = new KpiDashboardPersistenceEntity();

        if (dashboard.getId() != null) {
            entity.setId(dashboard.getId());
        }

        entity.setLaboratoryId(dashboard.getLaboratoryId());
        entity.setTimestamp(dashboard.getTimestamp());
        entity.setOverallHealthScore(dashboard.getOverallHealthScore());

        return entity;
    }
}