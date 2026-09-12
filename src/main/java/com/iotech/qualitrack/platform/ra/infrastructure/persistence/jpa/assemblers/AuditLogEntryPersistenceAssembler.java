package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.AuditLogEntryPersistenceEntity;

/**
 * Static assembler between audit log entry domain and persistence representations.
 */
public final class AuditLogEntryPersistenceAssembler {

    private AuditLogEntryPersistenceAssembler() {
    }

    public static AuditLogEntry toDomainFromPersistence(AuditLogEntryPersistenceEntity entity) {
        if (entity == null) return null;

        return new AuditLogEntry(
                entity.getId(),
                entity.getAction(),
                entity.getEntityType(),
                entity.getEntityId(),
                entity.getPerformedBy(),
                entity.getTimestamp(),
                entity.getDetails()
        );
    }

    public static AuditLogEntryPersistenceEntity toPersistenceFromDomain(AuditLogEntry auditLogEntry) {
        if (auditLogEntry == null) return null;

        var entity = new AuditLogEntryPersistenceEntity();

        if (auditLogEntry.getId() != null) {
            entity.setId(auditLogEntry.getId());
        }

        entity.setAction(auditLogEntry.getAction());
        entity.setEntityType(auditLogEntry.getEntityType());
        entity.setEntityId(auditLogEntry.getEntityId());
        entity.setPerformedBy(auditLogEntry.getPerformedBy());
        entity.setTimestamp(auditLogEntry.getTimestamp());
        entity.setDetails(auditLogEntry.getDetails());

        return entity;
    }
}