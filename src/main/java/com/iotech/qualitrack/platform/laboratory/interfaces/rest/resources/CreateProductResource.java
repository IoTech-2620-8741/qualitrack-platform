package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create product resource.
 */
@Schema(
        name = "CreateProductRequest",
        description = "Request payload for creating a new pharmaceutical product",
        example = "{\"code\": \"PRD-ASP-500\", \"name\": \"Aspirin 500mg\", \"description\": \"Pain reliever\", \"specifications\": \"Acetylsalicylic acid 500mg, blister pack\"}"
)
public record CreateProductResource(

        @Schema(description = "Internal catalog code", example = "PRD-ASP-500", minLength = 1, maxLength = 50)
        String code,

        @Schema(description = "Product name", example = "Aspirin 500mg", minLength = 1, maxLength = 150)
        String name,

        @Schema(description = "Detailed product description", example = "Pain reliever", maxLength = 500)
        String description,

        @Schema(description = "Technical specifications of the product", example = "Acetylsalicylic acid 500mg, blister pack", minLength = 1, maxLength = 1000)
        String specifications
) {
    /**
     * Validates the resource properties (Fail-Fast).
     * @throws IllegalArgumentException if required fields are missing or invalid.
     */
    public CreateProductResource {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Product code is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (specifications == null || specifications.isBlank()) {
            throw new IllegalArgumentException("Specifications are required");
        }
    }
}