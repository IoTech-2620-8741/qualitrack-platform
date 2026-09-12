package com.iotech.qualitrack.platform.equipment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Configure BPM Parameter resource.
 */
@Schema(
        name = "ConfigureBpmRequest",
        description = "Request payload for configuring an equipment's BPM parameter limits",
        example = "{\"parameterName\": \"TEMPERATURE\", \"minValue\": 2.0, \"maxValue\": 8.0, \"unit\": \"Celsius\"}"
)
public record ConfigureBpmResource(

        @Schema(description = "Critical variable or parameter name", example = "TEMPERATURE", minLength = 1, maxLength = 100)
        String parameterName,

        @Schema(description = "Minimum acceptable value", example = "2.0")
        Double minValue,

        @Schema(description = "Maximum acceptable value", example = "8.0")
        Double maxValue,

        @Schema(description = "Measurement unit", example = "Celsius", minLength = 1, maxLength = 20)
        String unit
) {
    /**
     * Validates the resource properties (Fail-Fast).
     * @throws IllegalArgumentException if required fields are missing or invalid.
     */
    public ConfigureBpmResource {
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("Parameter name is required");
        }
        if (minValue == null) {
            throw new IllegalArgumentException("Minimum value is required");
        }
        if (maxValue == null) {
            throw new IllegalArgumentException("Maximum value is required");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Unit is required");
        }
    }
}