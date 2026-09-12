package com.iotech.qualitrack.platform.subscription.domain.exceptions;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;

/**
 * Exception thrown when a subscription plan cannot be found.
 */
public class SubscriptionPlanNotFoundException extends RuntimeException {

    public SubscriptionPlanNotFoundException(Long planId) {
        super("Subscription plan with id '%d' was not found.".formatted(planId));
    }

    public SubscriptionPlanNotFoundException(PlanCode planCode, BillingCycle billingCycle) {
        super("Subscription plan '%s' with billing cycle '%s' was not found."
                .formatted(planCode, billingCycle));
    }

    public SubscriptionPlanNotFoundException(String stripePriceId) {
        super("Subscription plan with Stripe price id '%s' was not found.".formatted(stripePriceId));
    }
}