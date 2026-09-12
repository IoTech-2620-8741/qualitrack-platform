package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.tracking.domain.model.aggregates.EquipmentTelemetry;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.EquipmentTelemetryPersistenceEntity;

import java.util.List;

/**
 * Assembler that transforms equipment telemetry aggregate persistence entities into domain aggregates and vice versa.
 */
public final class EquipmentTelemetryPersistenceAssembler {
    private EquipmentTelemetryPersistenceAssembler() {
    }

    /**
     * Converts an equipment telemetry persistence entity into a domain aggregate.
     *
     * @param entity the equipment telemetry persistence entity
     * @param status the current telemetry status
     * @param measurements the latest measurements
     * @param historyPoints the telemetry history points
     * @return the equipment telemetry aggregate
     */
    public static EquipmentTelemetry toDomainFromPersistence(
            EquipmentTelemetryPersistenceEntity entity,
            EquipmentTelemetryStatus status,
            List<Measurement> measurements,
            List<TelemetryHistoryPoint> historyPoints
    ) {
        return new EquipmentTelemetry(
                entity.getId(),
                entity.getEquipmentId(),
                status,
                measurements,
                historyPoints
        );
    }

    /**
     * Converts an equipment telemetry aggregate into a persistence entity.
     *
     * @param equipmentTelemetry the equipment telemetry aggregate
     * @return the equipment telemetry persistence entity
     */
    public static EquipmentTelemetryPersistenceEntity toPersistenceFromDomain(
            EquipmentTelemetry equipmentTelemetry
    ) {
        var entity = new EquipmentTelemetryPersistenceEntity();

        entity.setId(equipmentTelemetry.getId());
        entity.setEquipmentId(equipmentTelemetry.getEquipmentId());

        return entity;
    }
}