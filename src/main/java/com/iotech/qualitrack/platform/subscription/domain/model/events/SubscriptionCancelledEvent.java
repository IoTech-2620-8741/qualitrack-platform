package com.iotech.qualitrack.platform.subscription.domain.model.events;

import com.iotech.qualitrack.platform.subscription.domain.model.aggregates.Subscription;

/**
 * Domain event published when a subscription is cancelled.
 */
public record SubscriptionCancelledEvent(
        Long subscriptionId,
        Long userId,
        Long laboratoryId,
        Long cancelledBy,
        String cancelledAt
) {
    public static SubscriptionCancelledEvent from(Subscription subscription) {
        return new SubscriptionCancelledEvent(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getLaboratoryId(),
                subscription.getCancelledBy(),
                subscription.getCancelledAt()
        );
    }
}