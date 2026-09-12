package com.iotech.qualitrack.platform.subscription.domain.repositories;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPlan;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for subscription plans.
 */
public interface SubscriptionPlanRepository {

    List<SubscriptionPlan> findAll();

    List<SubscriptionPlan> findAllActive();

    Optional<SubscriptionPlan> findById(Long id);

    Optional<SubscriptionPlan> findByCodeAndBillingCycle(PlanCode code, BillingCycle billingCycle);

    Optional<SubscriptionPlan> findByStripePriceId(String stripePriceId);

    SubscriptionPlan save(SubscriptionPlan plan);

    boolean existsById(Long id);
}