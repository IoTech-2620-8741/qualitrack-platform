package com.iotech.qualitrack.platform.iam.domain.exceptions;

/**
 * Exception thrown when a user attempts to sign in with invalid credentials.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid username or password.");
    }
}