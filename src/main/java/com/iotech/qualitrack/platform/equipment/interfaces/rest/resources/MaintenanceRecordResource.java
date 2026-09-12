package com.iotech.qualitrack.platform.equipment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Maintenance record resource.
 */
@Schema(
        name = "MaintenanceRecordResponse",
        description = "Maintenance record information response",
        example = "{\"id\": 1, \"equipmentId\": 1, \"maintenanceDate\": \"2026-05-20\", \"technicianName\": \"John Smith\", \"description\": \"Annual calibration and sensor replacement\", \"type\": \"PREVENTIVE\"}"
)
public record MaintenanceRecordResource(

        @Schema(description = "Maintenance record unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Associated equipment numeric identifier", example = "1")
        Long equipmentId,

        @Schema(description = "Date the maintenance was performed (ISO 8601)", example = "2026-05-20")
        String maintenanceDate,

        @Schema(description = "Name of the technician who performed the maintenance", example = "John Smith")
        String technicianName,

        @Schema(description = "Detailed description of the intervention", example = "Annual calibration and sensor replacement")
        String description,

        @Schema(description = "Type of maintenance performed", example = "PREVENTIVE")
        String type
) {
}