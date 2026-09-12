package com.iotech.qualitrack.platform.subscription.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Resource representing a subscription payment exposed through the REST API.
 */
public record SubscriptionPaymentResource(
        Long id,
        Long subscriptionId,
        String provider,
        String providerPaymentId,
        String stripeCheckoutSessionId,
        BigDecimal amount,
        String currency,
        String status,
        String paidAt
) {
}