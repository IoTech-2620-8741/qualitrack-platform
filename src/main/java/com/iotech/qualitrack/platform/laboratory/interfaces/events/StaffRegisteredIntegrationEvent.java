package com.iotech.qualitrack.platform.laboratory.interfaces.events;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.StaffRegisteredEvent;

/**
 * Integration event published by the Laboratory bounded context when a staff member is registered.
 *
 * <p>This event allows IAM, audit, or notification workflows to react without
 * depending on Laboratory internal domain events.</p>
 *
 * @param staffMemberId the registered staff member identifier
 * @param laboratoryId the laboratory identifier
 * @param fullName the staff member full name
 * @param role the assigned operational role
 * @param email the staff member email address
 */
public record StaffRegisteredIntegrationEvent(
        Long staffMemberId,
        Long laboratoryId,
        String fullName,
        String role,
        String email
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal staff registered domain event
     * @return the integration event
     */
    public static StaffRegisteredIntegrationEvent from(StaffRegisteredEvent event) {
        return new StaffRegisteredIntegrationEvent(
                event.staffMemberId(),
                event.laboratoryId(),
                event.fullName(),
                event.role(),
                event.email()
        );
    }
}