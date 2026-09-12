package com.iotech.qualitrack.platform.subscription.domain.model.commands;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;

/**
 * Command used to create a Stripe checkout session for a subscription plan.
 *
 * @param userId The user starting the checkout flow.
 * @param laboratoryId The laboratory associated with the subscription.
 * @param planCode The selected subscription plan.
 * @param billingCycle The selected billing cycle.
 * @param successUrl The URL Stripe redirects to after successful payment.
 * @param cancelUrl The URL Stripe redirects to after cancellation.
 */
public record CreateCheckoutSessionCommand(
        Long userId,
        Long laboratoryId,
        PlanCode planCode,
        BillingCycle billingCycle,
        String successUrl,
        String cancelUrl
) {

    public CreateCheckoutSessionCommand {
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
        if (successUrl == null || successUrl.isBlank()) {
            throw new IllegalArgumentException("Success URL is required");
        }
        if (cancelUrl == null || cancelUrl.isBlank()) {
            throw new IllegalArgumentException("Cancel URL is required");
        }
    }
}
