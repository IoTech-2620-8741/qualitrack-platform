package com.iotech.qualitrack.platform.subscription.interfaces.rest.resources;

/**
 * Resource representing a processed Stripe webhook response.
 */
public record StripeWebhookResource(
        String status
) {
}