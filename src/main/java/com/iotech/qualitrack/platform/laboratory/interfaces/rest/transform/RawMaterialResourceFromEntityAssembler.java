package com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.RawMaterialResource;

/**
 * Assembler to convert a RawMaterial entity to a RawMaterialResource.
 */
public class RawMaterialResourceFromEntityAssembler {

    /**
     * Converts a RawMaterial entity to a RawMaterialResource.
     *
     * @param entity The {@link RawMaterial} entity to convert.
     * @return The {@link RawMaterialResource} resource that results from the conversion.
     */
    public static RawMaterialResource toResourceFromEntity(RawMaterial entity) {
        return new RawMaterialResource(
                entity.getId(),
                entity.getLaboratoryId(),
                entity.getName(),
                entity.getCode(),
                entity.getSupplier(),
                entity.getBatchNumber(),
                entity.getExpirationDate(),
                entity.getCurrentStock(),
                entity.getUnit(),
                entity.getMinimumThreshold()
        );
    }
}