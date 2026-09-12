package com.iotech.qualitrack.platform.ra.interfaces.events;

import com.iotech.qualitrack.platform.ra.domain.model.events.AuditLogEntryRecordedEvent;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;

/**
 * Integration event published when an audit log entry has been recorded.
 *
 * <p>This is part of the published language of the Reporting & Audit bounded context.</p>
 *
 * @param auditLogEntryId The audit log entry identifier.
 * @param action The performed audit action.
 * @param entityType The audited entity type.
 * @param entityId The audited entity identifier.
 * @param performedBy The user or agent who performed the action.
 * @param occurredAt The timestamp when the audited action occurred.
 */
public record AuditLogEntryRecordedIntegrationEvent(
        Long auditLogEntryId,
        AuditAction action,
        String entityType,
        Long entityId,
        Long performedBy,
        String occurredAt
) {

    /**
     * Creates an integration event from the internal RA domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static AuditLogEntryRecordedIntegrationEvent from(AuditLogEntryRecordedEvent event) {
        return new AuditLogEntryRecordedIntegrationEvent(
                event.auditLogEntryId(),
                event.action(),
                event.entityType(),
                event.entityId(),
                event.performedBy(),
                event.occurredAt()
        );
    }
}