package com.iotech.qualitrack.platform.subscription.domain.repositories;

import com.iotech.qualitrack.platform.subscription.domain.model.aggregates.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for subscriptions.
 */
public interface SubscriptionRepository {

    Optional<Subscription> findById(Long id);

    Optional<Subscription> findActiveByLaboratoryId(Long laboratoryId);

    Optional<Subscription> findActiveByUserId(Long userId);

    Optional<Subscription> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);

    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);

    List<Subscription> findAllByLaboratoryId(Long laboratoryId);

    Subscription save(Subscription subscription);

    boolean existsById(Long id);
}