package com.iotech.qualitrack.platform.ca.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ca.domain.model.events.NotificationPreferenceUpdatedEvent;
import com.iotech.qualitrack.platform.ca.interfaces.events.NotificationPreferenceUpdatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal NotificationPreferenceUpdatedEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class NotificationPreferenceUpdatedEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new NotificationPreferenceUpdatedEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public NotificationPreferenceUpdatedEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal notification preference updated domain event.
     *
     * @param event the internal notification preference updated domain event
     */
    @EventListener(NotificationPreferenceUpdatedEvent.class)
    public void on(NotificationPreferenceUpdatedEvent event) {
        log.info(
                "Notification preference updated. preferenceId={}, userId={}, minimumSeverity={}",
                event.preferenceId(),
                event.userId(),
                event.minimumSeverity()
        );

        applicationEventPublisher.publishEvent(NotificationPreferenceUpdatedIntegrationEvent.from(event));
    }
}