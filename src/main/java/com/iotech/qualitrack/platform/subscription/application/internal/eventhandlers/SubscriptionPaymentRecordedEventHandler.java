package com.iotech.qualitrack.platform.subscription.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.subscription.domain.model.events.SubscriptionPaymentRecordedEvent;
import com.iotech.qualitrack.platform.subscription.interfaces.events.SubscriptionPaymentRecordedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles subscription payment recording domain events.
 */
@Service
@Slf4j
public class SubscriptionPaymentRecordedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionPaymentRecordedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener(SubscriptionPaymentRecordedEvent.class)
    public void on(SubscriptionPaymentRecordedEvent event) {
        log.info(
                "Subscription payment recorded: payment ID '{}', subscription ID '{}', status '{}'.",
                event.paymentId(),
                event.subscriptionId(),
                event.status()
        );

        eventPublisher.publishEvent(SubscriptionPaymentRecordedIntegrationEvent.from(event));
    }
}