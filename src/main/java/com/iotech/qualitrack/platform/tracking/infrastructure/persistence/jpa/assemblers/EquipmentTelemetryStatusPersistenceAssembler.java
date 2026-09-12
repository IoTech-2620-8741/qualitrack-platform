package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.EquipmentTelemetryStatusPersistenceEntity;

/**
 * Assembler that transforms equipment telemetry status persistence entities into domain entities and vice versa.
 */
public final class EquipmentTelemetryStatusPersistenceAssembler {
    private EquipmentTelemetryStatusPersistenceAssembler() {
    }

    /**
     * Converts an equipment telemetry status persistence entity into a domain entity.
     *
     * @param entity the equipment telemetry status persistence entity
     * @return the equipment telemetry status domain entity
     */
    public static EquipmentTelemetryStatus toDomainFromPersistence(
            EquipmentTelemetryStatusPersistenceEntity entity
    ) {
        return new EquipmentTelemetryStatus(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getIsOnline(),
                entity.getCurrentStatus(),
                entity.getLastHeartbeat(),
                entity.getCreatedAt() == null ? null : entity.getCreatedAt().toString()
        );
    }

    /**
     * Converts an equipment telemetry status domain entity into a persistence entity.
     *
     * @param status the equipment telemetry status domain entity
     * @return the equipment telemetry status persistence entity
     */
    public static EquipmentTelemetryStatusPersistenceEntity toPersistenceFromDomain(
            EquipmentTelemetryStatus status
    ) {
        var entity = new EquipmentTelemetryStatusPersistenceEntity();

        entity.setId(status.getId());
        entity.setEquipmentId(status.getEquipmentId());
        entity.setIsOnline(status.getIsOnline());
        entity.setCurrentStatus(status.getCurrentStatus());
        entity.setLastHeartbeat(status.getLastHeartbeat());

        return entity;
    }
}