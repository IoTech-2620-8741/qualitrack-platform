package com.iotech.qualitrack.platform.subscription.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.subscription.domain.model.events.SubscriptionCancelledEvent;
import com.iotech.qualitrack.platform.subscription.interfaces.events.SubscriptionCancelledIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles subscription cancellation domain events.
 */
@Service
@Slf4j
public class SubscriptionCancelledEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionCancelledEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener(SubscriptionCancelledEvent.class)
    public void on(SubscriptionCancelledEvent event) {
        log.info(
                "Subscription cancelled: subscription ID '{}', laboratory ID '{}', cancelled by '{}'.",
                event.subscriptionId(),
                event.laboratoryId(),
                event.cancelledBy()
        );

        eventPublisher.publishEvent(SubscriptionCancelledIntegrationEvent.from(event));
    }
}