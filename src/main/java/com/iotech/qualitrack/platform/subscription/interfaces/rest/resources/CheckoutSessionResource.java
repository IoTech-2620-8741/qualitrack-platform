package com.iotech.qualitrack.platform.subscription.interfaces.rest.resources;

/**
 * Response resource returned after creating a Stripe Checkout Session.
 */
public record CheckoutSessionResource(
        String checkoutUrl
) {
}