package com.iotech.qualitrack.platform.tracking.interfaces.rest.transform;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.EquipmentTelemetryStatusResource;

/**
 * Assembler that transforms equipment telemetry status domain entities into REST resources.
 */
public final class EquipmentTelemetryStatusResourceFromEntityAssembler {
    private EquipmentTelemetryStatusResourceFromEntityAssembler() {
    }

    /**
     * Converts an equipment telemetry status domain entity into its REST resource representation.
     *
     * @param entity the equipment telemetry status domain entity
     * @return the equipment telemetry status resource
     */
    public static EquipmentTelemetryStatusResource toResourceFromEntity(
            EquipmentTelemetryStatus entity
    ) {
        return new EquipmentTelemetryStatusResource(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getIsOnline(),
                entity.getCurrentStatus(),
                entity.getLastHeartbeat(),
                entity.getCreatedAt()
        );
    }
}