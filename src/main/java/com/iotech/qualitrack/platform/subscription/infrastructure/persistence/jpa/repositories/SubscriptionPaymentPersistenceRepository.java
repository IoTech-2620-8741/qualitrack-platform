package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.entities.SubscriptionPaymentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for subscription payments.
 */
public interface SubscriptionPaymentPersistenceRepository
        extends JpaRepository<SubscriptionPaymentPersistenceEntity, Long> {

    Optional<SubscriptionPaymentPersistenceEntity> findByProviderPaymentId(String providerPaymentId);

    Optional<SubscriptionPaymentPersistenceEntity> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);

    List<SubscriptionPaymentPersistenceEntity> findAllBySubscriptionIdOrderByIdDesc(Long subscriptionId);
}