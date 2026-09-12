package com.iotech.qualitrack.platform.ra.domain.model.commands;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;

/**
 * Command to request the recording of an audit log entry.
 *
 * @param action The performed audit action. Cannot be null.
 * @param entityType The affected entity type. Cannot be null or blank.
 * @param entityId The affected entity identifier, if applicable.
 * @param performedBy The numeric identifier of the actor. Cannot be null or less than 1.
 * @param details Optional details or serialized context for the action.
 */
public record RecordAuditLogEntryCommand(
        AuditAction action,
        String entityType,
        Long entityId,
        Long performedBy,
        String details
) {
    /**
     * Compact constructor for RecordAuditLogEntryCommand.
     * Enforces Fail-Fast validation.
     */
    public RecordAuditLogEntryCommand {
        if (action == null) {
            throw new IllegalArgumentException("action cannot be null");
        }
        if (entityType == null || entityType.isBlank()) {
            throw new IllegalArgumentException("entityType cannot be null or blank");
        }
        if (entityId != null && entityId <= 0) {
            throw new IllegalArgumentException("entityId cannot be less than 1");
        }
        if (performedBy != null && performedBy <= 0) {
            throw new IllegalArgumentException("performedBy must be positive when present");
        }
        if (details != null && details.isBlank()) {
            throw new IllegalArgumentException("details cannot be blank");
        }
    }
}
