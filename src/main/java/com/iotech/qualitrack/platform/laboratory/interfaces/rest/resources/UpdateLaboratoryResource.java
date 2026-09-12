package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Update laboratory resource.
 *
 * <p>Request payload for modifying an existing laboratory's profile.
 * Note: Immutable identifiers like RUC are excluded from updates.</p>
 */
@Schema(
        name = "UpdateLaboratoryRequest",
        description = "Request payload for updating an existing laboratory profile",
        example = "{\"name\": \"PharmaCorp Advanced\", \"phone\": \"+51987654322\", \"applicableRegulations\": [\"ISO-9001:2015\", \"BPA\"], \"address\": \"123 Tech Ave, Tower B, Lima\"}"
)
public record UpdateLaboratoryResource(

        @Schema(description = "Updated laboratory name", example = "PharmaCorp Advanced", minLength = 1, maxLength = 100)
        String name,

        @Schema(description = "Updated contact phone number", example = "+51987654322", minLength = 6, maxLength = 20)
        String phone,

        @Schema(description = "Updated list of applicable regulatory compliance codes", example = "[\"ISO-9001:2015\", \"BPA\"]")
        List<String> applicableRegulations,

        @Schema(description = "Updated physical address", example = "123 Tech Ave, Tower B, Lima", minLength = 1, maxLength = 250)
        String address
) {
    /**
     * Validates the resource properties (Fail-Fast).
     * @throws IllegalArgumentException if required fields are missing or invalid.
     */
    public UpdateLaboratoryResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone is required");
        }
        if (applicableRegulations == null || applicableRegulations.isEmpty()) {
            throw new IllegalArgumentException("At least one regulation code is required");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address is required");
        }
    }
}