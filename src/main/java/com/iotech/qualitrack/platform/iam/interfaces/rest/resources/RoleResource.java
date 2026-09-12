package com.iotech.qualitrack.platform.iam.interfaces.rest.resources;

/**
 * Resource representing an IAM role.
 *
 * @param id role identifier
 * @param name role name
 */
public record RoleResource(
        Long id,
        String name
) {
}