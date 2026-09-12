package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Create laboratory resource.
 */
@Schema(
        name = "CreateLaboratoryRequest",
        description = "Request payload for creating a new laboratory",
        example = "{\"name\": \"PharmaCorp\", \"ruc\": \"20123456789\", \"phone\": \"+51987654321\", \"applicableRegulations\": [\"ISO-9001\", \"BPA\"], \"address\": \"123 Tech Ave, Lima\"}"
)
public record CreateLaboratoryResource(

        @Schema(description = "Laboratory official name", example = "PharmaCorp", minLength = 1, maxLength = 100)
        String name,

        @Schema(description = "Tax identification number (RUC)", example = "20123456789", minLength = 11, maxLength = 11)
        String ruc,

        @Schema(description = "Full physical address", example = "123 Tech Ave, Lima", minLength = 1, maxLength = 250)
        String address,

        @Schema(description = "Contact phone number", example = "+51987654321", minLength = 6, maxLength = 20)
        String phone,

        @Schema(description = "List of applicable regulatory compliance codes", example = "[\"ISO-9001\", \"BPA\"]")
        List<String> applicableRegulations
) {
    /**
     * Validates the resource properties (Fail-Fast).
     * @throws IllegalArgumentException if any required field is invalid.
     */
    public CreateLaboratoryResource {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        if (ruc == null || ruc.isBlank()) throw new IllegalArgumentException("RUC is required");
        if (address == null || address.isBlank()) throw new IllegalArgumentException("Address is required");
        if (phone == null || phone.isBlank()) throw new IllegalArgumentException("Phone is required");
        if (applicableRegulations == null || applicableRegulations.isEmpty()) throw new IllegalArgumentException("At least one regulation code is required");
    }
}