package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ra.domain.model.events.AuditLogEntryRecordedEvent;
import com.iotech.qualitrack.platform.ra.interfaces.events.AuditLogEntryRecordedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer event handler for {@link AuditLogEntryRecordedEvent}.
 *
 * <p>Publishes the Reporting & Audit integration event after an audit log entry
 * is recorded, exposing RA's published language to other bounded contexts.</p>
 */
@Service
@Slf4j
public class AuditLogEntryRecordedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public AuditLogEntryRecordedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles the {@link AuditLogEntryRecordedEvent}.
     *
     * @param event the {@link AuditLogEntryRecordedEvent} published by the RA bounded context
     */
    @EventListener(AuditLogEntryRecordedEvent.class)
    public void on(AuditLogEntryRecordedEvent event) {
        log.info(
                "Audit log entry recorded: Entry ID='{}', Action='{}', EntityType='{}', Entity ID='{}', PerformedBy='{}', OccurredAt='{}'.",
                event.auditLogEntryId(),
                event.action(),
                event.entityType(),
                event.entityId(),
                event.performedBy(),
                event.occurredAt()
        );

        eventPublisher.publishEvent(AuditLogEntryRecordedIntegrationEvent.from(event));
    }
}