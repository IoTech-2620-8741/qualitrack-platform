package com.iotech.qualitrack.platform.iam.domain.exceptions;

/**
 * Exception thrown when an IAM user cannot be found.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("User with id '%s' was not found.".formatted(userId));
    }

    public UserNotFoundException(String username) {
        super("User with username '%s' was not found.".formatted(username));
    }
}