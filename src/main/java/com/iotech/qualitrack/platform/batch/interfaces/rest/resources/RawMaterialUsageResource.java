package com.iotech.qualitrack.platform.batch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Raw material usage resource.
 */
@Schema(
        name = "RawMaterialUsageResponse",
        description = "Raw material usage information response",
        example = "{\"id\": 1, \"batchId\": 1, \"rawMaterialId\": 5, \"rawMaterialName\": \"Purified Water\", \"quantityUsed\": 150.5, \"unit\": \"liters\", \"usageDate\": \"2026-05-12\"}"
)
public record RawMaterialUsageResource(

        @Schema(description = "Usage record unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Associated batch numeric identifier", example = "1")
        Long batchId,

        @Schema(description = "Consumed raw material numeric identifier", example = "5")
        Long rawMaterialId,

        @Schema(description = "Raw material display name", example = "Purified Water")
        String rawMaterialName,

        @Schema(description = "Consumed quantity", example = "150.5")
        Double quantityUsed,

        @Schema(description = "Measurement unit", example = "liters")
        String unit,

        @Schema(description = "Usage date in ISO 8601 format", example = "2026-05-12")
        String usageDate,
        java.math.BigDecimal stockBefore,
        java.math.BigDecimal stockAfter
) {
}
