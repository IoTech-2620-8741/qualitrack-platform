package com.iotech.qualitrack.platform.subscription.domain.model.events;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;

/**
 * Domain event published when a Stripe checkout session is created.
 *
 * @param userId The user starting checkout.
 * @param laboratoryId The laboratory associated with checkout.
 * @param planCode The selected plan.
 * @param billingCycle The selected billing cycle.
 * @param stripeCheckoutSessionId Stripe checkout session identifier.
 * @param checkoutUrl Stripe hosted checkout URL.
 */
public record CheckoutSessionCreatedEvent(
        Long userId,
        Long laboratoryId,
        PlanCode planCode,
        BillingCycle billingCycle,
        String stripeCheckoutSessionId,
        String checkoutUrl
) {
}