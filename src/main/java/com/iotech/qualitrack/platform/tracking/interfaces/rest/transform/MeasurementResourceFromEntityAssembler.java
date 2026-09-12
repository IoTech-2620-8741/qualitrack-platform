package com.iotech.qualitrack.platform.tracking.interfaces.rest.transform;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.MeasurementResource;

/**
 * Assembler that transforms measurement domain entities into REST resources.
 */
public final class MeasurementResourceFromEntityAssembler {
    private MeasurementResourceFromEntityAssembler() {
    }

    /**
     * Converts a measurement domain entity into its REST resource representation.
     *
     * @param entity the measurement domain entity
     * @return the measurement resource
     */
    public static MeasurementResource toResourceFromEntity(Measurement entity) {
        return new MeasurementResource(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getParameterName(),
                entity.getValue(),
                entity.getUnit(),
                entity.getTimestamp(),
                entity.getCreatedAt()
        );
    }
}