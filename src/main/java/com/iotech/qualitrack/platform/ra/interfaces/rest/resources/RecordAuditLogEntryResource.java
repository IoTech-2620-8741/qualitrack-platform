package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;

/**
 * Resource used to request recording of an audit log entry.
 *
 * @param action The performed audit action.
 * @param performedBy The numeric identifier of the actor.
 * @param details Additional action details.
 */
public record RecordAuditLogEntryResource(
        AuditAction action,
        Long performedBy,
        String details
) {
}