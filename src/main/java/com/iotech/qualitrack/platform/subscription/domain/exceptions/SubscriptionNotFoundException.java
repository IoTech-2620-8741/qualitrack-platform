package com.iotech.qualitrack.platform.subscription.domain.exceptions;

/**
 * Exception thrown when a subscription cannot be found.
 */
public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(Long subscriptionId) {
        super("Subscription with id '%d' was not found.".formatted(subscriptionId));
    }

    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}