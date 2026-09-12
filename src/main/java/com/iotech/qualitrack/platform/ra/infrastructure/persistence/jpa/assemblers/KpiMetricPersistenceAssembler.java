package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ra.domain.model.entities.KpiMetric;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.KpiMetricPersistenceEntity;

/**
 * Static assembler between KPI metric domain and persistence representations.
 */
public final class KpiMetricPersistenceAssembler {

    private KpiMetricPersistenceAssembler() {
    }

    public static KpiMetric toDomainFromPersistence(KpiMetricPersistenceEntity entity) {
        if (entity == null) return null;

        return new KpiMetric(
                entity.getId(),
                entity.getName(),
                entity.getValue(),
                entity.getUnit(),
                entity.getTargetValue(),
                entity.getStatus(),
                entity.getRecordedAt()
        );
    }

    public static KpiMetricPersistenceEntity toPersistenceFromDomain(Long dashboardId, KpiMetric metric) {
        if (metric == null) return null;

        var entity = new KpiMetricPersistenceEntity();

        if (metric.getId() != null) {
            entity.setId(metric.getId());
        }

        entity.setDashboardId(dashboardId);
        entity.setName(metric.getName());
        entity.setValue(metric.getValue());
        entity.setUnit(metric.getUnit());
        entity.setTargetValue(metric.getTargetValue());
        entity.setStatus(metric.getStatus());
        entity.setRecordedAt(metric.getRecordedAt());

        return entity;
    }
}