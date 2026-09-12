package com.iotech.qualitrack.platform.iam.domain.exceptions;

/**
 * Exception thrown when an inactive user attempts an operation that requires active status.
 */
public class InactiveUserException extends RuntimeException {

    public InactiveUserException(String username) {
        super("User '%s' is inactive.".formatted(username));
    }
}