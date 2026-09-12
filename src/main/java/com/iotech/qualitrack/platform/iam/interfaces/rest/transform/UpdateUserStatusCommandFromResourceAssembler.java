package com.iotech.qualitrack.platform.iam.interfaces.rest.transform;

import com.iotech.qualitrack.platform.iam.domain.model.commands.DeactivateUserCommand;
import com.iotech.qualitrack.platform.iam.interfaces.rest.resources.UpdateUserStatusResource;

/**
 * Assembler that transforms user status update REST resources into application commands.
 */
public final class UpdateUserStatusCommandFromResourceAssembler {

    private UpdateUserStatusCommandFromResourceAssembler() {
    }

    /**
     * Converts a user status update resource into a deactivate user command.
     *
     * @param userId the user numeric identifier from the route path
     * @param resource the user status update resource
     * @return the deactivate user command
     */
    public static DeactivateUserCommand toDeactivateCommandFromResource(
            Long userId,
            UpdateUserStatusResource resource
    ) {
        return new DeactivateUserCommand(userId);
    }
}