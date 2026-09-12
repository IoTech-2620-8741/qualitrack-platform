package com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.LaboratoryResource;

/**
 * Assembler to convert a Laboratory entity to a LaboratoryResource.
 */
public class LaboratoryResourceFromEntityAssembler {

    /**
     * Converts a Laboratory entity to a LaboratoryResource.
     *
     * @param entity The {@link Laboratory} entity to convert.
     * @return The {@link LaboratoryResource} resource that results from the conversion.
     */
    public static LaboratoryResource toResourceFromEntity(Laboratory entity) {
        return new LaboratoryResource(
                entity.getId(),
                entity.getName().name(),
                entity.getRuc(),
                entity.getPhone(),
                entity.getApplicableRegulations(),
                entity.getAddress().getAddress(),
                entity.getStatus().name()
        );
    }
}