package com.iotech.qualitrack.platform.subscription.interfaces.events;

import com.iotech.qualitrack.platform.subscription.domain.model.events.CheckoutSessionCreatedEvent;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;

/**
 * Integration event published when a Stripe checkout session is created.
 */
public record CheckoutSessionCreatedIntegrationEvent(
        Long userId,
        Long laboratoryId,
        PlanCode planCode,
        BillingCycle billingCycle,
        String stripeCheckoutSessionId,
        String checkoutUrl
) {
    public static CheckoutSessionCreatedIntegrationEvent from(CheckoutSessionCreatedEvent event) {
        return new CheckoutSessionCreatedIntegrationEvent(
                event.userId(),
                event.laboratoryId(),
                event.planCode(),
                event.billingCycle(),
                event.stripeCheckoutSessionId(),
                event.checkoutUrl()
        );
    }
}