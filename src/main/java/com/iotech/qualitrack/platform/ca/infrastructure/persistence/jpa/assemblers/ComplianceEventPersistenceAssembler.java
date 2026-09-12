package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ca.domain.model.entities.ComplianceEvent;
import com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.entities.ComplianceEventPersistenceEntity;

/**
 * Static assembler between compliance event domain and persistence representations.
 */
public final class ComplianceEventPersistenceAssembler {

    private ComplianceEventPersistenceAssembler() {
    }

    public static ComplianceEvent toDomainFromPersistence(ComplianceEventPersistenceEntity entity) {
        if (entity == null) return null;

        return new ComplianceEvent(
                entity.getId(),
                entity.getRelatedEntityId(),
                entity.getEventType(),
                entity.getDescription(),
                entity.getTimestamp(),
                entity.getResolvedBy()
        );
    }

    public static ComplianceEventPersistenceEntity toPersistenceFromDomain(ComplianceEvent event) {
        if (event == null) return null;

        var entity = new ComplianceEventPersistenceEntity();

        if (event.getId() != null) {
            entity.setId(event.getId());
        }

        entity.setRelatedEntityId(event.getRelatedEntityId());
        entity.setEventType(event.getEventType());
        entity.setDescription(event.getDescription());
        entity.setTimestamp(event.getTimestamp());
        entity.setResolvedBy(event.getResolvedBy());

        return entity;
    }
}