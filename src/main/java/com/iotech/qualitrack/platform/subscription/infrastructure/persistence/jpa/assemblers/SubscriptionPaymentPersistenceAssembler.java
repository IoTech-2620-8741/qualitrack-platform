package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPayment;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.entities.SubscriptionPaymentPersistenceEntity;

/**
 * Assembler for subscription payment persistence mapping.
 */
public class SubscriptionPaymentPersistenceAssembler {

    private SubscriptionPaymentPersistenceAssembler() {
    }

    public static SubscriptionPayment toDomainFromPersistence(SubscriptionPaymentPersistenceEntity entity) {
        if (entity == null) return null;

        return new SubscriptionPayment(
                entity.getId(),
                entity.getSubscriptionId(),
                entity.getProvider(),
                entity.getProviderPaymentId(),
                entity.getStripeCheckoutSessionId(),
                MoneyPersistenceAssembler.toDomainFromPersistence(entity.getAmount()),
                entity.getStatus(),
                entity.getPaidAt()
        );
    }

    public static SubscriptionPaymentPersistenceEntity toPersistenceFromDomain(SubscriptionPayment payment) {
        if (payment == null) return null;

        var entity = new SubscriptionPaymentPersistenceEntity();
        entity.setId(payment.getId());
        entity.setSubscriptionId(payment.getSubscriptionId());
        entity.setProvider(payment.getProvider());
        entity.setProviderPaymentId(payment.getProviderPaymentId());
        entity.setStripeCheckoutSessionId(payment.getStripeCheckoutSessionId());
        entity.setAmount(MoneyPersistenceAssembler.toPersistenceFromDomain(payment.getAmount()));
        entity.setStatus(payment.getStatus());
        entity.setPaidAt(payment.getPaidAt());

        return entity;
    }
}