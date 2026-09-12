package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;

/**
 * Resource representation of an audit log entry.
 *
 * @param id The unique numeric identifier of the audit log entry.
 * @param action The action performed.
 * @param entityType The affected entity type.
 * @param entityId The numeric identifier of the affected entity.
 * @param performedBy The numeric identifier of the actor.
 * @param timestamp The timestamp when the action occurred.
 * @param details Additional action details.
 */
public record AuditLogEntryResource(
        Long id,
        AuditAction action,
        String entityType,
        Long entityId,
        Long performedBy,
        String timestamp,
        String details
) {
}