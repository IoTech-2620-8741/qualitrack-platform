package com.iotech.qualitrack.platform.laboratory.domain.model.events;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.StaffMember;

/**
 * Domain event published when a staff member is deactivated.
 *
 * <p>Other bounded contexts can react to staff lifecycle changes without
 * directly depending on laboratory application services.</p>
 *
 * @param staffMemberId The numeric identity of the deactivated staff member.
 * @param laboratoryId The numeric identity of the laboratory they belong to.
 * @param fullName The full name of the staff member.
 * @param role The assigned operational role.
 * @param email The staff member email address.
 */
public record StaffDeactivatedEvent(
        Long staffMemberId,
        Long laboratoryId,
        String fullName,
        String role,
        String email
) {

    /**
     * Convenience factory that extracts all needed fields from a saved staff member.
     *
     * @param staffMember The saved staff member.
     * @return A fully populated {@link StaffDeactivatedEvent}.
     */
    public static StaffDeactivatedEvent from(StaffMember staffMember) {
        return new StaffDeactivatedEvent(
                staffMember.getId(),
                staffMember.getLaboratoryId(),
                staffMember.getFullName(),
                staffMember.getRole(),
                staffMember.getEmail()
        );
    }
}