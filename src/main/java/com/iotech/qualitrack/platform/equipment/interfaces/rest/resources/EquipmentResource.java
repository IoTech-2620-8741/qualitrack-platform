package com.iotech.qualitrack.platform.equipment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Equipment resource.
 *
 * <p>Represents the data payload returned to the client when querying
 * equipment information from the QualiTrack platform.</p>
 */
@Schema(
        name = "EquipmentResponse",
        description = "Equipment information response",
        example = "{\"id\": 1, \"laboratoryId\": 1, \"name\": \"Centrifuge 5000\", \"type\": \"CENTRIFUGE\", \"model\": \"C-5000X\", \"serialNumber\": \"SN-987654321\", \"status\": \"OPERATIONAL\", \"sensorExternalId\": \"IoT-SENS-001\"}"
)
public record EquipmentResource(

        @Schema(description = "Equipment unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Associated laboratory numeric identifier", example = "1")
        Long laboratoryId,

        @Schema(description = "Equipment display name", example = "Centrifuge 5000")
        String name,

        @Schema(description = "Equipment category or type", example = "CENTRIFUGE")
        String type,

        @Schema(description = "Manufacturer model", example = "C-5000X")
        String model,

        @Schema(description = "Unique serial number", example = "SN-987654321")
        String serialNumber,

        @Schema(description = "Current operational status", example = "OPERATIONAL")
        String status,

        @Schema(description = "External IoT sensor ID (if linked)", example = "IoT-SENS-001", nullable = true)
        String sensorExternalId
) {
}