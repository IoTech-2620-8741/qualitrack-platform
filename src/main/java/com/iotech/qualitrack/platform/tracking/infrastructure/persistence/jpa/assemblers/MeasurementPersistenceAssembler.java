package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.MeasurementPersistenceEntity;

/**
 * Assembler that transforms measurement persistence entities into domain entities and vice versa.
 */
public final class MeasurementPersistenceAssembler {
    private MeasurementPersistenceAssembler() {
    }

    /**
     * Converts a measurement persistence entity into a domain entity.
     *
     * @param entity the measurement persistence entity
     * @return the measurement domain entity
     */
    public static Measurement toDomainFromPersistence(MeasurementPersistenceEntity entity) {
        return new Measurement(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getParameterName(),
                entity.getValue(),
                entity.getUnit(),
                entity.getTimestamp(),
                entity.getCreatedAt() == null ? null : entity.getCreatedAt().toString()
        );
    }

    /**
     * Converts a measurement domain entity into a persistence entity.
     *
     * @param measurement the measurement domain entity
     * @return the measurement persistence entity
     */
    public static MeasurementPersistenceEntity toPersistenceFromDomain(Measurement measurement) {
        var entity = new MeasurementPersistenceEntity();

        entity.setId(measurement.getId());
        entity.setEquipmentId(measurement.getEquipmentId());
        entity.setParameterName(measurement.getParameterName());
        entity.setValue(measurement.getValue());
        entity.setUnit(measurement.getUnit());
        entity.setTimestamp(measurement.getTimestamp());

        return entity;
    }
}