package com.iotech.qualitrack.platform.subscription.domain.repositories;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPayment;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for subscription payments.
 */
public interface SubscriptionPaymentRepository {

    Optional<SubscriptionPayment> findById(Long id);

    Optional<SubscriptionPayment> findByProviderPaymentId(String providerPaymentId);

    Optional<SubscriptionPayment> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);

    List<SubscriptionPayment> findAllBySubscriptionId(Long subscriptionId);

    SubscriptionPayment save(SubscriptionPayment payment);

    boolean existsById(Long id);
}