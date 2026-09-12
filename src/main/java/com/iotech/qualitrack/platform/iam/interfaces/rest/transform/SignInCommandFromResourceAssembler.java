package com.iotech.qualitrack.platform.iam.interfaces.rest.transform;

import com.iotech.qualitrack.platform.iam.domain.model.commands.SignInCommand;
import com.iotech.qualitrack.platform.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler that maps sign-in REST resources to domain commands.
 */
public class SignInCommandFromResourceAssembler {

    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(
                resource.username(),
                resource.password()
        );
    }
}