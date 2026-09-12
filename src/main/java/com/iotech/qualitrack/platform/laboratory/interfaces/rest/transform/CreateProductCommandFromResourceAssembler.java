package com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateProductCommand;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.CreateProductResource;

/**
 * Assembler to convert a CreateProductResource to a CreateProductCommand.
 */
public class CreateProductCommandFromResourceAssembler {

    public static CreateProductCommand toCommandFromResource(Long laboratoryId, CreateProductResource resource) {
        return new CreateProductCommand(
                laboratoryId,
                resource.code(),
                resource.name(),
                resource.description(),
                resource.specifications()
        );
    }
}