package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Pharmaceutical product resource.
 *
 * <p>Represents the data payload returned to the client when querying
 * pharmaceutical product information from the QualiTrack platform.</p>
 */
@Schema(
        name = "PharmaceuticalProductResponse",
        description = "Pharmaceutical product information response",
        example = "{\"id\": 1, \"laboratoryId\": 1, \"code\": \"PRD-ASP-500\", \"name\": \"Aspirin 500mg\", \"description\": \"Pain reliever and fever reducer\", \"specifications\": \"Acetylsalicylic acid 500mg, blister pack\", \"active\": true}"
)
public record PharmaceuticalProductResource(

        @Schema(description = "Product unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Associated laboratory numeric identifier", example = "1")
        Long laboratoryId,

        @Schema(description = "Internal catalog code", example = "PRD-ASP-500")
        String code,

        @Schema(description = "Product name", example = "Aspirin 500mg")
        String name,

        @Schema(description = "Detailed product description", example = "Pain reliever and fever reducer")
        String description,

        @Schema(description = "Technical specifications of the product", example = "Acetylsalicylic acid 500mg, blister pack")
        String specifications,

        @Schema(description = "Indicates whether this product is currently active in the catalog", example = "true")
        boolean active
) {
}