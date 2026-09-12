package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Staff member resource.
 *
 * <p>Represents the data payload returned to the client when querying
 * staff member information from the QualiTrack platform.</p>
 */
@Schema(
        name = "StaffMemberResponse",
        description = "Staff member information response",
        example = "{\"id\": 1, \"laboratoryId\": 1, \"fullName\": \"Jane Doe\", \"role\": \"Quality Inspector\", \"email\": \"jane.doe@pharmacorp.com\", \"active\": true}"
)
public record StaffMemberResource(

        @Schema(description = "Staff member unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Associated laboratory numeric identifier", example = "1")
        Long laboratoryId,

        @Schema(description = "Full legal name", example = "Jane Doe")
        String fullName,

        @Schema(description = "Assigned operational role", example = "Quality Inspector")
        String role,

        @Schema(description = "Corporate email address", example = "jane.doe@pharmacorp.com")
        String email,

        @Schema(description = "Is the staff member currently active", example = "true")
        boolean active
) {
}