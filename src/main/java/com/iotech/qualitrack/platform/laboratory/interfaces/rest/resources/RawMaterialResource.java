package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Raw material resource.
 *
 * <p>Represents the data payload returned to the client when querying
 * raw material inventory information from the QualiTrack platform.</p>
 */
@Schema(
        name = "RawMaterialResponse",
        description = "Raw material information response",
        example = "{\"id\": 1, \"laboratoryId\": 1, \"name\": \"Distilled Water\", \"code\": \"RM-DW-001\", \"supplier\": \"AquaChem\", \"batchNumber\": \"B-202606\", \"expirationDate\": \"2027-06-06\", \"currentStock\": 500, \"unit\": \"Liters\", \"minimumThreshold\": 50}"
)
public record RawMaterialResource(

        @Schema(description = "Raw material unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Associated laboratory numeric identifier", example = "1")
        Long laboratoryId,

        @Schema(description = "Raw material name", example = "Distilled Water")
        String name,

        @Schema(description = "Internal catalog code", example = "RM-DW-001")
        String code,

        @Schema(description = "External supplier name", example = "AquaChem")
        String supplier,

        @Schema(description = "Batch or lot number", example = "B-202606")
        String batchNumber,

        @Schema(description = "Expiration date in ISO 8601 format", example = "2027-06-06")
        String expirationDate,

        @Schema(description = "Current available stock quantity", example = "500")
        BigDecimal currentStock,

        @Schema(description = "Measurement unit", example = "Liters")
        String unit,

        @Schema(description = "Minimum stock threshold to trigger alerts", example = "50")
        BigDecimal minimumThreshold
) {
}
