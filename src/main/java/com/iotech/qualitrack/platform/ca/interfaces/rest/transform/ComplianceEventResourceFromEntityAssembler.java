package com.iotech.qualitrack.platform.ca.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ca.domain.model.entities.ComplianceEvent;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.ComplianceEventResource;

/**
 * Assembler to convert ComplianceEvent domain entities into REST resources.
 */
public class ComplianceEventResourceFromEntityAssembler {

    public static ComplianceEventResource toResourceFromEntity(ComplianceEvent entity) {
        return new ComplianceEventResource(
                entity.getId(),
                entity.getRelatedEntityId(),
                entity.getEventType(),
                entity.getDescription(),
                entity.getTimestamp(),
                entity.getResolvedBy()
        );
    }
}