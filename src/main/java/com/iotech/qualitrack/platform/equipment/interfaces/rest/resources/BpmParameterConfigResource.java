package com.iotech.qualitrack.platform.equipment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * BPM Parameter Configuration resource.
 */
@Schema(
        name = "BpmParameterConfigResponse",
        description = "BPM Parameter configuration response",
        example = "{\"id\": 1, \"equipmentId\": 1, \"parameterName\": \"TEMPERATURE\", \"minValue\": 2.0, \"maxValue\": 8.0, \"unit\": \"Celsius\"}"
)
public record BpmParameterConfigResource(

        @Schema(description = "Configuration unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Associated equipment numeric identifier", example = "1")
        Long equipmentId,

        @Schema(description = "Critical variable or parameter name", example = "TEMPERATURE")
        String parameterName,

        @Schema(description = "Minimum acceptable value", example = "2.0")
        Double minValue,

        @Schema(description = "Maximum acceptable value", example = "8.0")
        Double maxValue,

        @Schema(description = "Measurement unit", example = "Celsius")
        String unit
) {
}