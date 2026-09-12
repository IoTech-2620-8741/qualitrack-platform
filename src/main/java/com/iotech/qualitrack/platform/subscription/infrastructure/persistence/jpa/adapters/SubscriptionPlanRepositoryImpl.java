package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPlan;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import com.iotech.qualitrack.platform.subscription.domain.repositories.SubscriptionPlanRepository;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.assemblers.SubscriptionPlanPersistenceAssembler;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.repositories.SubscriptionPlanPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for subscription plan repository.
 */
@Repository
public class SubscriptionPlanRepositoryImpl implements SubscriptionPlanRepository {

    private final SubscriptionPlanPersistenceRepository persistenceRepository;

    public SubscriptionPlanRepositoryImpl(SubscriptionPlanPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<SubscriptionPlan> findAll() {
        return persistenceRepository.findAll()
                .stream()
                .map(SubscriptionPlanPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<SubscriptionPlan> findAllActive() {
        return persistenceRepository.findAllByActiveTrue()
                .stream()
                .map(SubscriptionPlanPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<SubscriptionPlan> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(SubscriptionPlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SubscriptionPlan> findByCodeAndBillingCycle(PlanCode code, BillingCycle billingCycle) {
        return persistenceRepository.findByCodeAndBillingCycle(code, billingCycle)
                .map(SubscriptionPlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SubscriptionPlan> findByStripePriceId(String stripePriceId) {
        return persistenceRepository.findByStripePriceId(stripePriceId)
                .map(SubscriptionPlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public SubscriptionPlan save(SubscriptionPlan plan) {
        var entity = SubscriptionPlanPersistenceAssembler.toPersistenceFromDomain(plan);
        var saved = persistenceRepository.save(entity);
        return SubscriptionPlanPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}