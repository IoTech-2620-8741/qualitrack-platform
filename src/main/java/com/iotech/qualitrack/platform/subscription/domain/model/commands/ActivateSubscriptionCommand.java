package com.iotech.qualitrack.platform.subscription.domain.model.commands;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;

/**
 * Command used to activate a subscription after Stripe confirms payment.
 *
 * @param userId The subscribed user.
 * @param laboratoryId The subscribed laboratory.
 * @param planCode The activated plan.
 * @param billingCycle The activated billing cycle.
 * @param stripeCustomerId Stripe customer identifier.
 * @param stripeSubscriptionId Stripe subscription identifier.
 * @param stripeCheckoutSessionId Stripe checkout session identifier.
 * @param currentPeriodStart The subscription current period start.
 * @param currentPeriodEnd The subscription current period end.
 */
public record ActivateSubscriptionCommand(
        Long userId,
        Long laboratoryId,
        PlanCode planCode,
        BillingCycle billingCycle,
        String stripeCustomerId,
        String stripeSubscriptionId,
        String stripeCheckoutSessionId,
        String currentPeriodStart,
        String currentPeriodEnd
) {

    public ActivateSubscriptionCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be a positive number");
        }
        if (laboratoryId != null && laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id must be a positive number");
        }
        if (planCode == null) {
            throw new IllegalArgumentException("Plan code is required");
        }
        if (billingCycle == null) {
            throw new IllegalArgumentException("Billing cycle is required");
        }
        if (stripeCustomerId == null || stripeCustomerId.isBlank()) {
            throw new IllegalArgumentException("Stripe customer id is required");
        }
        if (stripeSubscriptionId == null || stripeSubscriptionId.isBlank()) {
            throw new IllegalArgumentException("Stripe subscription id is required");
        }
        if (stripeCheckoutSessionId == null || stripeCheckoutSessionId.isBlank()) {
            throw new IllegalArgumentException("Stripe checkout session id is required");
        }
    }
}
