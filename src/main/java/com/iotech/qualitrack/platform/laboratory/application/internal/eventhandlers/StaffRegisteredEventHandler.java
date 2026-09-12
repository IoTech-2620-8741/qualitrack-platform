package com.iotech.qualitrack.platform.laboratory.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.StaffRegisteredEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.StaffRegisteredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal StaffRegisteredEvent events and publishes the corresponding integration event.
 */
@Service
@Slf4j
public class StaffRegisteredEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new StaffRegisteredEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public StaffRegisteredEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal staff registered domain event.
     *
     * @param event the internal staff registered domain event
     */
    @EventListener(StaffRegisteredEvent.class)
    public void on(StaffRegisteredEvent event) {
        log.info(
                "Staff member registered. staffMemberId={}, laboratoryId={}, fullName={}, role={}",
                event.staffMemberId(),
                event.laboratoryId(),
                event.fullName(),
                event.role()
        );

        applicationEventPublisher.publishEvent(StaffRegisteredIntegrationEvent.from(event));
    }
}