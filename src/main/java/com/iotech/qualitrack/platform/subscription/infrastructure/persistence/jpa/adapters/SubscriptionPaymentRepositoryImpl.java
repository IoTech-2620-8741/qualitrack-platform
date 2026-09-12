package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPayment;
import com.iotech.qualitrack.platform.subscription.domain.repositories.SubscriptionPaymentRepository;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.assemblers.SubscriptionPaymentPersistenceAssembler;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.repositories.SubscriptionPaymentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for subscription payment repository.
 */
@Repository
public class SubscriptionPaymentRepositoryImpl implements SubscriptionPaymentRepository {

    private final SubscriptionPaymentPersistenceRepository persistenceRepository;

    public SubscriptionPaymentRepositoryImpl(SubscriptionPaymentPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<SubscriptionPayment> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(SubscriptionPaymentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SubscriptionPayment> findByProviderPaymentId(String providerPaymentId) {
        return persistenceRepository.findByProviderPaymentId(providerPaymentId)
                .map(SubscriptionPaymentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SubscriptionPayment> findByStripeCheckoutSessionId(String stripeCheckoutSessionId) {
        return persistenceRepository.findByStripeCheckoutSessionId(stripeCheckoutSessionId)
                .map(SubscriptionPaymentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<SubscriptionPayment> findAllBySubscriptionId(Long subscriptionId) {
        return persistenceRepository.findAllBySubscriptionIdOrderByIdDesc(subscriptionId)
                .stream()
                .map(SubscriptionPaymentPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public SubscriptionPayment save(SubscriptionPayment payment) {
        var entity = SubscriptionPaymentPersistenceAssembler.toPersistenceFromDomain(payment);
        var saved = persistenceRepository.save(entity);
        return SubscriptionPaymentPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}