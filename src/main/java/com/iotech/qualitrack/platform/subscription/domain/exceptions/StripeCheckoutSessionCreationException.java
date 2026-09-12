package com.iotech.qualitrack.platform.subscription.domain.exceptions;

/**
 * Exception thrown when a Stripe checkout session cannot be created.
 */
public class StripeCheckoutSessionCreationException extends RuntimeException {

    public StripeCheckoutSessionCreationException(String message) {
        super(message);
    }

    public StripeCheckoutSessionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}