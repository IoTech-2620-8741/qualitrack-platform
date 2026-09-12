package com.iotech.qualitrack.platform.subscription.domain.model.events;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPayment;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;

/**
 * Domain event published when a subscription payment is recorded.
 */
public record SubscriptionPaymentRecordedEvent(
        Long paymentId,
        Long subscriptionId,
        String providerPaymentId,
        String stripeCheckoutSessionId,
        BigDecimal amount,
        String currency,
        PaymentStatus status
) {
    public static SubscriptionPaymentRecordedEvent from(SubscriptionPayment payment) {
        return new SubscriptionPaymentRecordedEvent(
                payment.getId(),
                payment.getSubscriptionId(),
                payment.getProviderPaymentId(),
                payment.getStripeCheckoutSessionId(),
                payment.getAmount().amount(),
                payment.getAmount().currency(),
                payment.getStatus()
        );
    }
}