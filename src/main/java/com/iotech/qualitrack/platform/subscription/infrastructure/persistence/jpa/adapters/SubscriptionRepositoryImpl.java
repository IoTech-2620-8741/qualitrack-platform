package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.subscription.domain.model.aggregates.Subscription;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.iotech.qualitrack.platform.subscription.domain.repositories.SubscriptionRepository;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.assemblers.SubscriptionPersistenceAssembler;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.repositories.SubscriptionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for subscription repository.
 */
@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionPersistenceRepository persistenceRepository;

    public SubscriptionRepositoryImpl(SubscriptionPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Subscription> findActiveByLaboratoryId(Long laboratoryId) {
        return persistenceRepository.findFirstByLaboratoryIdAndStatusOrderByIdDesc(
                laboratoryId,
                SubscriptionStatus.ACTIVE
        ).map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Subscription> findActiveByUserId(Long userId) {
        return persistenceRepository.findFirstByUserIdAndStatusOrderByIdDesc(
                userId,
                SubscriptionStatus.ACTIVE
        ).map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Subscription> findByStripeCheckoutSessionId(String stripeCheckoutSessionId) {
        return persistenceRepository.findByStripeCheckoutSessionId(stripeCheckoutSessionId)
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId) {
        return persistenceRepository.findByStripeSubscriptionId(stripeSubscriptionId)
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Subscription> findAllByLaboratoryId(Long laboratoryId) {
        return persistenceRepository.findAllByLaboratoryIdOrderByIdDesc(laboratoryId)
                .stream()
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Subscription save(Subscription subscription) {
        var entity = SubscriptionPersistenceAssembler.toPersistenceFromDomain(subscription);
        var saved = persistenceRepository.save(entity);
        return SubscriptionPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}