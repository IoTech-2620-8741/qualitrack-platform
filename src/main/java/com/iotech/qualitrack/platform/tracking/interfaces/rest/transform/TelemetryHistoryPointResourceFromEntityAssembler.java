package com.iotech.qualitrack.platform.tracking.interfaces.rest.transform;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.TelemetryHistoryPointResource;

/**
 * Assembler that transforms telemetry history point domain entities into REST resources.
 */
public final class TelemetryHistoryPointResourceFromEntityAssembler {
    private TelemetryHistoryPointResourceFromEntityAssembler() {
    }

    /**
     * Converts a telemetry history point domain entity into its REST resource representation.
     *
     * @param entity the telemetry history point domain entity
     * @return the telemetry history point resource
     */
    public static TelemetryHistoryPointResource toResourceFromEntity(
            TelemetryHistoryPoint entity
    ) {
        return new TelemetryHistoryPointResource(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getParameterName(),
                entity.getRecordedValue(),
                entity.getTimestamp(),
                entity.getIsAnomaly(),
                entity.getCreatedAt()
        );
    }
}