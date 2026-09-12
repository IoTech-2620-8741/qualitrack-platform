package com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.DeactivateStaffCommand;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.UpdateStaffStatusResource;

/**
 * Assembler that transforms staff status update REST resources into application commands.
 */
public final class UpdateStaffStatusCommandFromResourceAssembler {

    private UpdateStaffStatusCommandFromResourceAssembler() {
    }

    /**
     * Converts a staff status update resource into a deactivate staff command.
     *
     * @param staffId the staff numeric identifier from the route path
     * @param resource the staff status update resource
     * @return the deactivate staff command
     */
    public static DeactivateStaffCommand toDeactivateCommandFromResource(
            Long staffId,
            UpdateStaffStatusResource resource
    ) {
        return new DeactivateStaffCommand(staffId);
    }
}