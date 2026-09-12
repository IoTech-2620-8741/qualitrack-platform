package com.iotech.qualitrack.platform.subscription.domain.model.queries;

/**
 * Query used to retrieve payments associated with a subscription.
 *
 * @param subscriptionId The subscription identifier.
 */
public record GetPaymentsBySubscriptionIdQuery(
        Long subscriptionId
) {

    public GetPaymentsBySubscriptionIdQuery {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("Subscription id must be a positive number");
        }
    }
}