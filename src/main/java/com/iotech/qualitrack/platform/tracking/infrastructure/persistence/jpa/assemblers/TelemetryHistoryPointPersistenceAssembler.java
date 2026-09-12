package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.TelemetryHistoryPointPersistenceEntity;

/**
 * Assembler that transforms telemetry history point persistence entities into domain entities and vice versa.
 */
public final class TelemetryHistoryPointPersistenceAssembler {
    private TelemetryHistoryPointPersistenceAssembler() {
    }

    /**
     * Converts a telemetry history point persistence entity into a domain entity.
     *
     * @param entity the telemetry history point persistence entity
     * @return the telemetry history point domain entity
     */
    public static TelemetryHistoryPoint toDomainFromPersistence(
            TelemetryHistoryPointPersistenceEntity entity
    ) {
        return new TelemetryHistoryPoint(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getParameterName(),
                entity.getRecordedValue(),
                entity.getTimestamp(),
                entity.getIsAnomaly(),
                entity.getCreatedAt() == null ? null : entity.getCreatedAt().toString()
        );
    }

    /**
     * Converts a telemetry history point domain entity into a persistence entity.
     *
     * @param historyPoint the telemetry history point domain entity
     * @return the telemetry history point persistence entity
     */
    public static TelemetryHistoryPointPersistenceEntity toPersistenceFromDomain(
            TelemetryHistoryPoint historyPoint
    ) {
        var entity = new TelemetryHistoryPointPersistenceEntity();

        entity.setId(historyPoint.getId());
        entity.setEquipmentId(historyPoint.getEquipmentId());
        entity.setParameterName(historyPoint.getParameterName());
        entity.setRecordedValue(historyPoint.getRecordedValue());
        entity.setTimestamp(historyPoint.getTimestamp());
        entity.setIsAnomaly(historyPoint.getIsAnomaly());

        return entity;
    }
}