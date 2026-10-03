package com.iotech.qualitrack.platform.equipment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body to register a maintenance performed on an equipment.
 */
@Schema(name = "RegisterMaintenanceRequest", description = "Maintenance intervention performed on the equipment")
public record RegisterMaintenanceResource(
        @Schema(description = "Date the maintenance was performed (ISO 8601), not in the future", example = "2026-05-20")
        @NotBlank @Size(max = 10) String maintenanceDate,
        @Schema(description = "Name of the technician", example = "John Smith")
        @NotBlank @Size(max = 150) String technicianName,
        @Schema(description = "Detailed description of the work done", example = "Annual calibration and sensor replacement")
        @NotBlank @Size(max = 1000) String description,
        @Schema(description = "Type of maintenance", example = "PREVENTIVE",
                allowableValues = {"PREVENTIVE", "CORRECTIVE", "CALIBRATION", "INSPECTION", "OTHER"})
        @NotBlank String type
) {
}
