package com.iotech.qualitrack.platform.subscription.application.internal.outboundservices.acl;

/**
 * Result returned after creating a Stripe Checkout Session.
 *
 * @param checkoutSessionId Stripe Checkout Session identifier
 * @param checkoutUrl Stripe-hosted checkout URL
 */
public record StripeCheckoutSessionResult(
        String checkoutSessionId,
        String checkoutUrl
) {
}