package com.iotech.qualitrack.platform.iam.domain.exceptions;

/**
 * Exception thrown when an IAM role cannot be found.
 */
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleName) {
        super("Role '%s' was not found.".formatted(roleName));
    }
}