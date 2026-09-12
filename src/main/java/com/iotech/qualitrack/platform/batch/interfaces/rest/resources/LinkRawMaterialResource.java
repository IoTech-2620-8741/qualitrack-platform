package com.iotech.qualitrack.platform.batch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Link raw material resource.
 */
@Schema(
        name = "LinkRawMaterialRequest",
        description = "Request payload for linking raw material usage to a production batch",
        example = "{\"rawMaterialId\": 5, \"quantityUsed\": 150.5, \"unit\": \"kg\"}"
)
public record LinkRawMaterialResource(

        @Schema(description = "Consumed raw material numeric identifier", example = "5")
        Long rawMaterialId,

        @Schema(description = "Consumed quantity", example = "150.5", minimum = "0")
        Double quantityUsed,
        String unit
) {
    public LinkRawMaterialResource {
        if (unit == null || unit.isBlank()) throw new IllegalArgumentException("Consumption unit is required");
        if (rawMaterialId == null || rawMaterialId <= 0) {
            throw new IllegalArgumentException("Raw material ID is required and must be greater than zero");
        }
        if (quantityUsed == null || !Double.isFinite(quantityUsed) || quantityUsed <= 0) {
            throw new IllegalArgumentException("Quantity used must be provided and greater than zero");
        }
    }
}
