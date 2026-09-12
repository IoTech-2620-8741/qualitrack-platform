package com.iotech.qualitrack.platform.subscription.domain.exceptions;

/**
 * Exception thrown when a subscription payment cannot be found.
 */
public class SubscriptionPaymentNotFoundException extends RuntimeException {

    public SubscriptionPaymentNotFoundException(Long paymentId) {
        super("Subscription payment with id '%d' was not found.".formatted(paymentId));
    }

    public SubscriptionPaymentNotFoundException(String identifier) {
        super("Subscription payment with identifier '%s' was not found.".formatted(identifier));
    }
}