package com.iotech.qualitrack.platform.subscription.domain.exceptions;

/**
 * Exception thrown when no active subscription exists for a user or laboratory.
 */
public class ActiveSubscriptionNotFoundException extends RuntimeException {

    public ActiveSubscriptionNotFoundException(String ownerType, Long ownerId) {
        super("Active subscription for %s with id '%d' was not found.".formatted(ownerType, ownerId));
    }
}