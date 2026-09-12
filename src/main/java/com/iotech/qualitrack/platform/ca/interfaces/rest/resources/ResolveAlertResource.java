package com.iotech.qualitrack.platform.ca.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ResolveAlertRequest",
        description = "Request payload for resolving a deviation alert",
        example = "{\"resolvedBy\": 10, \"resolutionNotes\": \"Equipment recalibrated and batch quality review completed.\"}"
)
public record ResolveAlertResource(
        @Schema(description = "User numeric identifier resolving the alert", example = "10")
        Long resolvedBy,

        @Schema(description = "Corrective action or resolution notes", example = "Equipment recalibrated and batch quality review completed.")
        String resolutionNotes
) {
    public ResolveAlertResource {
        if (resolvedBy == null || resolvedBy <= 0) {
            throw new IllegalArgumentException("resolvedBy cannot be null or less than 1");
        }
        if (resolutionNotes == null || resolutionNotes.isBlank()) {
            throw new IllegalArgumentException("resolutionNotes cannot be null or blank");
        }
    }
}