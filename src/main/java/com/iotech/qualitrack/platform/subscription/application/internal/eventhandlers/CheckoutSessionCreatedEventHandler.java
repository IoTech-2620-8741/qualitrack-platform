package com.iotech.qualitrack.platform.subscription.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.subscription.domain.model.events.CheckoutSessionCreatedEvent;
import com.iotech.qualitrack.platform.subscription.interfaces.events.CheckoutSessionCreatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles checkout session creation domain events.
 *
 * <p>Publishes the corresponding integration event so other bounded contexts
 * can react without depending on subscription internals.</p>
 */
@Service
@Slf4j
public class CheckoutSessionCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public CheckoutSessionCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener(CheckoutSessionCreatedEvent.class)
    public void on(CheckoutSessionCreatedEvent event) {
        log.info(
                "Checkout session created for laboratory ID '{}', plan '{}', billing cycle '{}'.",
                event.laboratoryId(),
                event.planCode(),
                event.billingCycle()
        );

        eventPublisher.publishEvent(CheckoutSessionCreatedIntegrationEvent.from(event));
    }
}