package com.iotech.qualitrack.platform.iam.interfaces.rest.transform;

import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembler that maps IAM role entities to REST resources.
 */
public class RoleResourceFromEntityAssembler {

    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(
                role.getId(),
                role.getName().name()
        );
    }
}