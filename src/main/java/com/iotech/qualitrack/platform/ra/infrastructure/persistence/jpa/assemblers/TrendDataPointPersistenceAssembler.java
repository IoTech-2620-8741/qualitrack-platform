package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ra.domain.model.entities.TrendDataPoint;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.TrendDataPointPersistenceEntity;

/**
 * Static assembler between trend data point domain and persistence representations.
 */
public final class TrendDataPointPersistenceAssembler {

    private TrendDataPointPersistenceAssembler() {
    }

    public static TrendDataPoint toDomainFromPersistence(TrendDataPointPersistenceEntity entity) {
        if (entity == null) return null;

        return new TrendDataPoint(
                entity.getTimestamp(),
                entity.getRecordedValue(),
                entity.getUpperThreshold(),
                entity.getLowerThreshold()
        );
    }

    public static TrendDataPointPersistenceEntity toPersistenceFromDomain(
            Long trendId,
            TrendDataPoint dataPoint
    ) {
        if (dataPoint == null) return null;

        var entity = new TrendDataPointPersistenceEntity();

        entity.setTrendId(trendId);
        entity.setTimestamp(dataPoint.getTimestamp());
        entity.setRecordedValue(dataPoint.getRecordedValue());
        entity.setUpperThreshold(dataPoint.getUpperThreshold());
        entity.setLowerThreshold(dataPoint.getLowerThreshold());

        return entity;
    }
}