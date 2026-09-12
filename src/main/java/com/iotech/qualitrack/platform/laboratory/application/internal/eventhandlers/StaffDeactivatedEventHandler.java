package com.iotech.qualitrack.platform.laboratory.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.StaffDeactivatedEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.StaffDeactivatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles staff deactivation domain events and publishes Laboratory integration events.
 */
@Service
@Slf4j
public class StaffDeactivatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public StaffDeactivatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles staff deactivation events.
     *
     * @param event The internal Laboratory domain event.
     */
    @EventListener(StaffDeactivatedEvent.class)
    public void on(StaffDeactivatedEvent event) {
        log.info(
                "Staff member deactivated: Staff ID='{}', Laboratory ID='{}', Full Name='{}', Role='{}', Email='{}'.",
                event.staffMemberId(),
                event.laboratoryId(),
                event.fullName(),
                event.role(),
                event.email()
        );

        eventPublisher.publishEvent(StaffDeactivatedIntegrationEvent.from(event));
    }
}