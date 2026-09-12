package com.iotech.qualitrack.platform.subscription.domain.model.events;

import com.iotech.qualitrack.platform.subscription.domain.model.aggregates.Subscription;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;

/**
 * Domain event published when a subscription becomes active.
 */
public record SubscriptionActivatedEvent(
        Long subscriptionId,
        Long userId,
        Long laboratoryId,
        PlanCode planCode,
        BillingCycle billingCycle,
        String stripeCustomerId,
        String stripeSubscriptionId
) {
    public static SubscriptionActivatedEvent from(Subscription subscription) {
        return new SubscriptionActivatedEvent(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getLaboratoryId(),
                subscription.getPlanCode(),
                subscription.getBillingCycle(),
                subscription.getStripeCustomerId(),
                subscription.getStripeSubscriptionId()
        );
    }
}