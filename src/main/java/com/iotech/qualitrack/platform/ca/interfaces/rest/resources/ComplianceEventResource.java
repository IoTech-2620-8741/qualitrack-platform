package com.iotech.qualitrack.platform.ca.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventType;

public record ComplianceEventResource(
        Long id,
        Long relatedEntityId,
        ComplianceEventType eventType,
        String description,
        String timestamp,
        Long resolvedBy
) {
}