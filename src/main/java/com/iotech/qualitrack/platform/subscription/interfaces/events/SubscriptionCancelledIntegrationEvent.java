package com.iotech.qualitrack.platform.subscription.interfaces.events;

import com.iotech.qualitrack.platform.subscription.domain.model.events.SubscriptionCancelledEvent;

/**
 * Integration event published when a subscription is cancelled.
 */
public record SubscriptionCancelledIntegrationEvent(
        Long subscriptionId,
        Long userId,
        Long laboratoryId,
        Long cancelledBy,
        String cancelledAt
) {
    public static SubscriptionCancelledIntegrationEvent from(SubscriptionCancelledEvent event) {
        return new SubscriptionCancelledIntegrationEvent(
                event.subscriptionId(),
                event.userId(),
                event.laboratoryId(),
                event.cancelledBy(),
                event.cancelledAt()
        );
    }
}