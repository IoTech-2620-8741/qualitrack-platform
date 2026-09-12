package com.iotech.qualitrack.platform.subscription.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.subscription.domain.model.events.SubscriptionActivatedEvent;
import com.iotech.qualitrack.platform.subscription.interfaces.events.SubscriptionActivatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles subscription activation domain events.
 */
@Service
@Slf4j
public class SubscriptionActivatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionActivatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener(SubscriptionActivatedEvent.class)
    public void on(SubscriptionActivatedEvent event) {
        log.info(
                "Subscription activated: subscription ID '{}', laboratory ID '{}', plan '{}'.",
                event.subscriptionId(),
                event.laboratoryId(),
                event.planCode()
        );

        eventPublisher.publishEvent(SubscriptionActivatedIntegrationEvent.from(event));
    }
}