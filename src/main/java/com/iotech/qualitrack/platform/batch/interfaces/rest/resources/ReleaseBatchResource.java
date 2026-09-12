package com.iotech.qualitrack.platform.batch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Release batch resource.
 */
@Schema(
        name = "ReleaseBatchRequest",
        description = "Request payload for releasing a production batch",
        example = "{\"releaseDate\": \"2026-05-20\", \"notes\": \"All quality controls passed\"}"
)
public record ReleaseBatchResource(

        @Schema(description = "Batch release date in ISO 8601 format", example = "2026-05-20")
        String releaseDate,

        @Schema(description = "Final quality control notes", example = "All quality controls passed")
        String notes
) {
    public ReleaseBatchResource {
        if (releaseDate == null || releaseDate.isBlank()) {
            throw new IllegalArgumentException("Release date is required");
        }
        if (notes == null || notes.isBlank()) {
            throw new IllegalArgumentException("Notes are required");
        }
    }
}