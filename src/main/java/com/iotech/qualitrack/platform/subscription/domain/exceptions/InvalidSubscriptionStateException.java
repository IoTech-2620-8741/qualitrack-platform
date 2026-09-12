package com.iotech.qualitrack.platform.subscription.domain.exceptions;

/**
 * Exception thrown when a subscription operation is not valid for the current state.
 */
public class InvalidSubscriptionStateException extends RuntimeException {

    public InvalidSubscriptionStateException(String message) {
        super(message);
    }
}