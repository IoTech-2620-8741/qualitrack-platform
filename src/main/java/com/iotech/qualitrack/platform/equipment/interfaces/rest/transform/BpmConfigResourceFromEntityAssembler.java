package com.iotech.qualitrack.platform.equipment.interfaces.rest.transform;

import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.BpmParameterConfigResource;

/**
 * Assembler to convert a BpmParameterConfig entity to a BpmParameterConfigResource.
 */
public class BpmConfigResourceFromEntityAssembler {

    /**
     * Converts a BpmParameterConfig entity to a BpmParameterConfigResource.
     *
     * @param entity The {@link BpmParameterConfig} entity to convert.
     * @return The {@link BpmParameterConfigResource} resource that results from the conversion.
     */
    public static BpmParameterConfigResource toResourceFromEntity(BpmParameterConfig entity) {
        return new BpmParameterConfigResource(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getParameterName().name(),
                entity.getMinValue(),
                entity.getMaxValue(),
                entity.getUnit()
        );
    }
}