package com.iotech.qualitrack.platform.subscription.domain.model.commands;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;

/**
 * Command used to record a Stripe payment transaction.
 *
 * @param subscriptionId The internal subscription identifier.
 * @param stripePaymentIntentId Stripe payment intent identifier.
 * @param stripeCheckoutSessionId Stripe checkout session identifier.
 * @param amount The paid amount.
 * @param currency The ISO currency code.
 * @param status The payment status.
 * @param paidAt The payment timestamp.
 */
public record RecordStripePaymentCommand(
        Long subscriptionId,
        String stripePaymentIntentId,
        String stripeCheckoutSessionId,
        BigDecimal amount,
        String currency,
        PaymentStatus status,
        String paidAt
) {

    public RecordStripePaymentCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("Subscription id must be a positive number");
        }
        if (stripeCheckoutSessionId == null || stripeCheckoutSessionId.isBlank()) {
            throw new IllegalArgumentException("Stripe checkout session id is required");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("Payment status is required");
        }
    }
}