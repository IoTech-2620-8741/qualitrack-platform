package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.entities.SubscriptionPlanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for subscription plans.
 */
public interface SubscriptionPlanPersistenceRepository
        extends JpaRepository<SubscriptionPlanPersistenceEntity, Long> {

    List<SubscriptionPlanPersistenceEntity> findAllByActiveTrue();

    Optional<SubscriptionPlanPersistenceEntity> findByCodeAndBillingCycle(
            PlanCode code,
            BillingCycle billingCycle
    );

    Optional<SubscriptionPlanPersistenceEntity> findByStripePriceId(String stripePriceId);
}