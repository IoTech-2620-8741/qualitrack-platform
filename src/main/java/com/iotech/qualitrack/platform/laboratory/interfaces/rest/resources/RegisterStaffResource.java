package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Register staff resource.
 */
@Schema(
        name = "RegisterStaffRequest",
        description = "Request payload for registering a new staff member",
        example = "{\"fullName\": \"Jane Doe\", \"role\": \"Quality Inspector\", \"email\": \"jane.doe@pharmacorp.com\"}"
)
public record RegisterStaffResource(



        @Schema(description = "Full legal name", example = "Jane Doe", minLength = 1, maxLength = 100)
        String fullName,

        @Schema(description = "Operational or professional role", example = "Quality Inspector", minLength = 1, maxLength = 50)
        String role,

        @Schema(description = "Corporate email address", example = "jane.doe@pharmacorp.com", minLength = 5, maxLength = 100)
        String email
) {
    /**
     * Validates the resource properties (Fail-Fast).
     * @throws IllegalArgumentException if any field is missing or invalid.
     */
    public RegisterStaffResource {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role is required");
        }
    }
}