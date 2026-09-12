package com.iotech.qualitrack.platform.iam.domain.exceptions;

/**
 * Exception thrown when trying to register an already existing username.
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username '%s' is already registered.".formatted(username));
    }
}