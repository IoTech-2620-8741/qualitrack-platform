package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for subscriptions.
 */
public interface SubscriptionPersistenceRepository
        extends JpaRepository<SubscriptionPersistenceEntity, Long> {

    Optional<SubscriptionPersistenceEntity> findFirstByLaboratoryIdAndStatusOrderByIdDesc(
            Long laboratoryId,
            SubscriptionStatus status
    );

    Optional<SubscriptionPersistenceEntity> findFirstByUserIdAndStatusOrderByIdDesc(
            Long userId,
            SubscriptionStatus status
    );

    Optional<SubscriptionPersistenceEntity> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);

    Optional<SubscriptionPersistenceEntity> findByStripeSubscriptionId(String stripeSubscriptionId);

    List<SubscriptionPersistenceEntity> findAllByLaboratoryIdOrderByIdDesc(Long laboratoryId);
}