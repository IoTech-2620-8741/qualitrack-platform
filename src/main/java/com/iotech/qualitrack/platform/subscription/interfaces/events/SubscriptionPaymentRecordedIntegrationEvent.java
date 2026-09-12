package com.iotech.qualitrack.platform.subscription.interfaces.events;

import com.iotech.qualitrack.platform.subscription.domain.model.events.SubscriptionPaymentRecordedEvent;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;

/**
 * Integration event published when a subscription payment is recorded.
 */
public record SubscriptionPaymentRecordedIntegrationEvent(
        Long paymentId,
        Long subscriptionId,
        String providerPaymentId,
        String stripeCheckoutSessionId,
        BigDecimal amount,
        String currency,
        PaymentStatus status
) {
    public static SubscriptionPaymentRecordedIntegrationEvent from(SubscriptionPaymentRecordedEvent event) {
        return new SubscriptionPaymentRecordedIntegrationEvent(
                event.paymentId(),
                event.subscriptionId(),
                event.providerPaymentId(),
                event.stripeCheckoutSessionId(),
                event.amount(),
                event.currency(),
                event.status()
        );
    }
}