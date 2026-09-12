package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.AuditLogEntryResource;

/**
 * Assembler that transforms audit log entry domain entities into REST resources.
 */
public final class AuditLogEntryResourceFromEntityAssembler {
    private AuditLogEntryResourceFromEntityAssembler() {
    }

    /**
     * Converts an audit log entry entity into its REST resource representation.
     *
     * @param entity the audit log entry entity
     * @return the audit log entry resource
     */
    public static AuditLogEntryResource toResourceFromEntity(AuditLogEntry entity) {
        return new AuditLogEntryResource(
                entity.getId(),
                entity.getAction(),
                entity.getEntityType(),
                entity.getEntityId(),
                entity.getPerformedBy(),
                entity.getTimestamp(),
                entity.getDetails()
        );
    }
}