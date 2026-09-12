package com.iotech.qualitrack.platform.laboratory.domain.model.commands;

/**
 * Command to request the registration of a new Staff Member.
 *
 * @param laboratoryId The numeric identifier of the laboratory. Cannot be null or less than 1.
 * @param fullName The full legal name. Cannot be null or blank.
 * @param role The professional role. Cannot be null or blank.
 * @param email The corporate email address. Cannot be null or blank.
 */
public record RegisterStaffCommand(
        Long laboratoryId,
        String fullName,
        String role,
        String email
) {
    /**
     * Compact constructor for RegisterStaffCommand.
     * Enforces Fail-Fast validation.
     */
    public RegisterStaffCommand {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be null or less than 1");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName cannot be null or blank");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("role cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be null or blank");
        }
    }
}