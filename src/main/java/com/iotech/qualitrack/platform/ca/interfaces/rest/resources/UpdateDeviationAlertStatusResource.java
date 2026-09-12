package com.iotech.qualitrack.platform.ca.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "UpdateDeviationAlertStatusRequest",
        description = "Request payload for updating a deviation alert status",
        example = "{\"status\": \"RESOLVED\", \"performedBy\": 10, \"resolutionNotes\": \"Equipment recalibrated and batch quality review completed.\"}"
)
public record UpdateDeviationAlertStatusResource(
        @Schema(description = "Target alert status", example = "RESOLVED")
        AlertStatus status,

        @Schema(description = "User numeric identifier performing the state update", example = "10")
        Long performedBy,

        @Schema(description = "Resolution notes required when status is RESOLVED")
        String resolutionNotes
) {
    public UpdateDeviationAlertStatusResource {
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }
        if (performedBy == null || performedBy <= 0) {
            throw new IllegalArgumentException("performedBy cannot be null or less than 1");
        }
        if (status == AlertStatus.RESOLVED && (resolutionNotes == null || resolutionNotes.isBlank())) {
            throw new IllegalArgumentException("resolutionNotes cannot be null or blank when status is RESOLVED");
        }
    }
}