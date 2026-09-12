package com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.CriticalVariable;
import com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.entities.BpmParameterConfigPersistenceEntity;

/**
 * Static assembler between BPM parameter config domain and persistence representations.
 */
public final class BpmParameterConfigPersistenceAssembler {

    private BpmParameterConfigPersistenceAssembler() {
    }

    public static BpmParameterConfig toDomainFromPersistence(BpmParameterConfigPersistenceEntity entity) {
        if (entity == null) return null;

        return new BpmParameterConfig(
                entity.getId(),
                entity.getEquipmentId(),
                new CriticalVariable(entity.getParameterName()),
                entity.getMinValue(),
                entity.getMaxValue(),
                entity.getUnit()
        );
    }

    public static BpmParameterConfigPersistenceEntity toPersistenceFromDomain(BpmParameterConfig config) {
        if (config == null) return null;

        var entity = new BpmParameterConfigPersistenceEntity();

        if (config.getId() != null) {
            entity.setId(config.getId());
        }

        entity.setEquipmentId(config.getEquipmentId());
        entity.setParameterName(config.getParameterName().name());
        entity.setMinValue(config.getMinValue());
        entity.setMaxValue(config.getMaxValue());
        entity.setUnit(config.getUnit());

        return entity;
    }
}