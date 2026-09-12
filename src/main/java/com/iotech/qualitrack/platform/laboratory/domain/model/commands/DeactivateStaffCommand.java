package com.iotech.qualitrack.platform.laboratory.domain.model.commands;

/**
 * Command to request the deactivation of a Staff Member.
 *
 * @param staffMemberId The numeric identifier of the staff member to deactivate. Cannot be null or less than 1.
 */
public record DeactivateStaffCommand(
        Long staffMemberId
) {
    /**
     * Compact constructor for DeactivateStaffCommand.
     * Enforces Fail-Fast validation.
     */
    public DeactivateStaffCommand {
        if (staffMemberId == null || staffMemberId <= 0) {
            throw new IllegalArgumentException("staffMemberId cannot be null or less than 1");
        }
    }
}