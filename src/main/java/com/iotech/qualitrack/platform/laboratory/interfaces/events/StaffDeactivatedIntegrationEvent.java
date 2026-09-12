package com.iotech.qualitrack.platform.laboratory.interfaces.events;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.StaffDeactivatedEvent;

/**
 * Integration event published when a staff member is deactivated.
 *
 * <p>This is part of the published language of the Laboratory bounded context.</p>
 *
 * @param staffMemberId The deactivated staff member identifier.
 * @param laboratoryId The laboratory identifier that owns the staff member.
 * @param fullName The full name of the staff member.
 * @param role The assigned operational role.
 * @param email The staff member email address.
 */
public record StaffDeactivatedIntegrationEvent(
        Long staffMemberId,
        Long laboratoryId,
        String fullName,
        String role,
        String email
) {

    /**
     * Creates an integration event from the internal Laboratory domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static StaffDeactivatedIntegrationEvent from(StaffDeactivatedEvent event) {
        return new StaffDeactivatedIntegrationEvent(
                event.staffMemberId(),
                event.laboratoryId(),
                event.fullName(),
                event.role(),
                event.email()
        );
    }
}