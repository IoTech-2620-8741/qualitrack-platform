package com.iotech.qualitrack.platform.iam.interfaces.rest.resources;

/**
 * Resource used to request a user status update.
 *
 * @param active whether the user account should be active
 */
public record UpdateUserStatusResource(
        Boolean active
) {
}