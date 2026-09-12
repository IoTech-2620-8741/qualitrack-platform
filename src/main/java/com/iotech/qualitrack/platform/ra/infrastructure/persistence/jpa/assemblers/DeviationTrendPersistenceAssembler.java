package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.domain.model.entities.TrendDataPoint;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.DeviationTrendPersistenceEntity;

import java.util.List;

/**
 * Static assembler between deviation trend domain and persistence representations.
 */
public final class DeviationTrendPersistenceAssembler {

    private DeviationTrendPersistenceAssembler() {
    }

    public static DeviationTrend toDomainFromPersistence(
            DeviationTrendPersistenceEntity entity,
            List<TrendDataPoint> dataPoints
    ) {
        if (entity == null) return null;

        return new DeviationTrend(
                entity.getId(),
                entity.getParameterName(),
                entity.getEquipmentId(),
                entity.getTrendDirection(),
                dataPoints
        );
    }

    public static DeviationTrendPersistenceEntity toPersistenceFromDomain(DeviationTrend trend) {
        if (trend == null) return null;

        var entity = new DeviationTrendPersistenceEntity();

        if (trend.getId() != null) {
            entity.setId(trend.getId());
        }

        entity.setParameterName(trend.getParameterName());
        entity.setEquipmentId(trend.getEquipmentId());
        entity.setTrendDirection(trend.getTrendDirection());

        return entity;
    }
}