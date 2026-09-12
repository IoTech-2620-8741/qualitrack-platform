package com.iotech.qualitrack.platform.ra.domain.model.events;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;

/**
 * Event raised when a new audit log entry is recorded.
 *
 * @param auditLogEntryId The numeric identifier of the audit log entry.
 * @param action The recorded audit action.
 * @param entityType The affected entity type.
 * @param entityId The affected entity identifier, if applicable.
 * @param performedBy The numeric identifier of the user or system actor.
 * @param occurredAt The timestamp when the action occurred.
 */
public record AuditLogEntryRecordedEvent(
        Long auditLogEntryId,
        AuditAction action,
        String entityType,
        Long entityId,
        Long performedBy,
        String occurredAt
) {
    /**
     * Compact constructor for AuditLogEntryRecordedEvent.
     * Enforces Fail-Fast validation.
     */
    public AuditLogEntryRecordedEvent {
        if (auditLogEntryId == null || auditLogEntryId <= 0) {
            throw new IllegalArgumentException("auditLogEntryId cannot be null or less than 1");
        }
        if (action == null) {
            throw new IllegalArgumentException("action cannot be null");
        }
        if (entityType == null || entityType.isBlank()) {
            throw new IllegalArgumentException("entityType cannot be null or blank");
        }
        if (entityId != null && entityId <= 0) {
            throw new IllegalArgumentException("entityId cannot be less than 1");
        }
        if (performedBy == null || performedBy <= 0) {
            throw new IllegalArgumentException("performedBy cannot be null or less than 1");
        }
        if (occurredAt == null || occurredAt.isBlank()) {
            throw new IllegalArgumentException("occurredAt cannot be null or blank");
        }
    }
}