package com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.RegisterStaffCommand;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.RegisterStaffResource;

/**
 * Assembler to convert a RegisterStaffResource to a RegisterStaffCommand.
 */
public class RegisterStaffCommandFromResourceAssembler {

    /**
     * Converts a RegisterStaffResource to a RegisterStaffCommand.
     *
     * @param resource The {@link RegisterStaffResource} resource to convert.
     * @return The {@link RegisterStaffCommand} command that results from the conversion.
     */
    public static RegisterStaffCommand toCommandFromResource(RegisterStaffResource resource, Long laboratoryId) {
        return new RegisterStaffCommand(
                laboratoryId,
                resource.fullName(),
                resource.role(),
                resource.email()
                );
    }
}