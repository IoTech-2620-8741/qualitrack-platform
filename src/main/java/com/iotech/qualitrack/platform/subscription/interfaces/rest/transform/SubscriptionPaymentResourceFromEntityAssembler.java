package com.iotech.qualitrack.platform.subscription.interfaces.rest.transform;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPayment;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.resources.SubscriptionPaymentResource;

/**
 * Assembler that maps SubscriptionPayment domain entities to REST resources.
 */
public class SubscriptionPaymentResourceFromEntityAssembler {

    public static SubscriptionPaymentResource toResourceFromEntity(SubscriptionPayment entity) {
        return new SubscriptionPaymentResource(
                entity.getId(),
                entity.getSubscriptionId(),
                entity.getProvider().name(),
                entity.getProviderPaymentId(),
                entity.getStripeCheckoutSessionId(),
                entity.getAmount().amount(),
                entity.getAmount().currency(),
                entity.getStatus().name(),
                entity.getPaidAt()
        );
    }
}