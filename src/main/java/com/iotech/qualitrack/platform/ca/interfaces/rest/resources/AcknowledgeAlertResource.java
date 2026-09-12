package com.iotech.qualitrack.platform.ca.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AcknowledgeAlertRequest",
        description = "Request payload for acknowledging a deviation alert",
        example = "{\"acknowledgedBy\": 10}"
)
public record AcknowledgeAlertResource(
        @Schema(description = "User numeric identifier acknowledging the alert", example = "10")
        Long acknowledgedBy
) {
    public AcknowledgeAlertResource {
        if (acknowledgedBy == null || acknowledgedBy <= 0) {
            throw new IllegalArgumentException("acknowledgedBy cannot be null or less than 1");
        }
    }
}