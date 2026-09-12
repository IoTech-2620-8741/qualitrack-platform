package com.iotech.qualitrack.platform.ca.domain.model.queries;

/**
 * Query to get compliance events by related entity id.
 *
 * @param relatedEntityId The ID of the related domain entity. Cannot be null or less than 1.
 */
public record GetComplianceEventsByRelatedEntityIdQuery(Long relatedEntityId,
        com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventSubject subject) {
    /**
     * Compact constructor for GetComplianceEventsByRelatedEntityIdQuery.
     * Validates that the relatedEntityId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if relatedEntityId is null or less than 1.
     */
    public GetComplianceEventsByRelatedEntityIdQuery {
        java.util.Objects.requireNonNull(subject, "A compliance event subject is required");
        if (relatedEntityId == null || relatedEntityId <= 0) {
            throw new IllegalArgumentException("Related entity id is required and must be greater than 0.");
        }
    }
}
