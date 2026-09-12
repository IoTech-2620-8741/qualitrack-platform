package com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateLaboratoryCommand;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.CreateLaboratoryResource;

/**
 * Assembler to convert a CreateLaboratoryResource to a CreateLaboratoryCommand.
 */
public class CreateLaboratoryCommandFromResourceAssembler {

    /**
     * Converts a CreateLaboratoryResource to a CreateLaboratoryCommand.
     *
     * @param resource The {@link CreateLaboratoryResource} resource to convert.
     * @return The {@link CreateLaboratoryCommand} command that results from the conversion.
     */
    public static CreateLaboratoryCommand toCommandFromResource(CreateLaboratoryResource resource) {
        return new CreateLaboratoryCommand(
                resource.name(),
                resource.ruc(),
                resource.address(),
                resource.phone(),
                resource.applicableRegulations()
                );
    }
}