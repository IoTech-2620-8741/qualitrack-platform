package com.iotech.qualitrack.platform.subscription.interfaces.rest.resources;

/**
 * Request resource used to create a Stripe Checkout Session.
 */
public record CreateCheckoutSessionResource(
        Long userId,
        Long laboratoryId,
        String planCode,
        String billingCycle,
        String successUrl,
        String cancelUrl
) {
}