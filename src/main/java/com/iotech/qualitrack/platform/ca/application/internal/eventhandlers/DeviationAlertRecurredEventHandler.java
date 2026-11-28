package com.iotech.qualitrack.platform.ca.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ca.domain.model.events.DeviationAlertRecurredEvent;
import com.iotech.qualitrack.platform.ca.interfaces.events.DeviationAlertRecurredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Publishes the recurrence of an alert to the other bounded contexts.
 */
@Slf4j
@Service
public class DeviationAlertRecurredEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    public DeviationAlertRecurredEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(DeviationAlertRecurredEvent.class)
    public void on(DeviationAlertRecurredEvent event) {
        log.warn("Deviation alert recurred. alertId={}, parameterName={}, severity={}, deviations={}",
                event.alertId(), event.parameterName(), event.severity(), event.deviationCount());
        applicationEventPublisher.publishEvent(DeviationAlertRecurredIntegrationEvent.from(event));
    }
}
