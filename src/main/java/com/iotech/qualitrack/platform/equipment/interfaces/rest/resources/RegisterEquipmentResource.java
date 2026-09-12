package com.iotech.qualitrack.platform.equipment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Register equipment resource.
 */
@Schema(
        name = "RegisterEquipmentRequest",
        description = "Request payload for registering a new equipment",
        example = "{\"laboratoryId\": 1, \"name\": \"Centrifuge 5000\", \"type\": \"CENTRIFUGE\", \"model\": \"C-5000X\", \"serialNumber\": \"SN-987654321\"}"
)
public record RegisterEquipmentResource(

        @Schema(description = "Target laboratory numeric identifier", example = "1")
        Long laboratoryId,

        @Schema(description = "Equipment display name", example = "Centrifuge 5000", minLength = 1, maxLength = 150)
        String name,

        @Schema(description = "Equipment category or type", example = "CENTRIFUGE", minLength = 1, maxLength = 100)
        String type,

        @Schema(description = "Manufacturer model", example = "C-5000X", minLength = 1, maxLength = 100)
        String model,

        @Schema(description = "Unique serial number", example = "SN-987654321", minLength = 1, maxLength = 50)
        String serialNumber
) {
    /**
     * Validates the resource properties (Fail-Fast).
     * @throws IllegalArgumentException if required fields are missing or invalid.
     */
    public RegisterEquipmentResource {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory ID is required and must be greater than zero");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Equipment name is required");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Equipment type is required");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model is required");
        }
        if (serialNumber == null || serialNumber.isBlank()) {
            throw new IllegalArgumentException("Serial number is required");
        }
    }
}