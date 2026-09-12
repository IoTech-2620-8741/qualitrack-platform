package com.iotech.qualitrack.platform.subscription.domain.model.queries;

/**
 * Query used to retrieve a subscription using its Stripe checkout session identifier.
 *
 * @param stripeCheckoutSessionId Stripe checkout session identifier.
 */
public record GetSubscriptionByStripeCheckoutSessionIdQuery(
        String stripeCheckoutSessionId
) {

    public GetSubscriptionByStripeCheckoutSessionIdQuery {
        if (stripeCheckoutSessionId == null || stripeCheckoutSessionId.isBlank()) {
            throw new IllegalArgumentException("Stripe checkout session id is required");
        }
    }
}