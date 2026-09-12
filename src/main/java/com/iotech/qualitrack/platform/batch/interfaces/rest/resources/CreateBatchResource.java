package com.iotech.qualitrack.platform.batch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create batch resource.
 */
@Schema(
        name = "CreateBatchRequest",
        description = "Request payload for creating a new production batch",
        example = "{\"labId\": 1, \"productId\": 10, \"batchNumber\": \"LOT-2026-A\", \"quantity\": 5000.0, \"startDate\": \"2026-05-12\", \"notes\": \"Standard production run\"}"
)
public record CreateBatchResource(

        @Schema(description = "Target laboratory numeric identifier", example = "1")
        Long labId,

        @Schema(description = "Target pharmaceutical product numeric identifier", example = "10")
        Long productId,

        @Schema(description = "Batch traceability number", example = "LOT-2026-A", minLength = 1, maxLength = 50)
        String batchNumber,

        @Schema(description = "Batch quantity", example = "5000.0", minimum = "0")
        Double quantity,

        @Schema(description = "Batch start date in ISO 8601 format", example = "2026-05-12")
        String startDate,

        @Schema(description = "Optional manufacturing notes", example = "Standard production run", nullable = true)
        String notes,
        @Schema(description = "Production quantity unit, independent of raw material stock units", example = "kg")
        String unit
) {
    public CreateBatchResource {
        if (labId == null || labId <= 0) {
            throw new IllegalArgumentException("Laboratory ID is required and must be greater than zero");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID is required and must be greater than zero");
        }
        if (batchNumber == null || batchNumber.isBlank()) {
            throw new IllegalArgumentException("Batch number is required");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be provided and greater than zero");
        }
        if (startDate == null || startDate.isBlank()) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (notes != null && notes.isBlank()) notes = null;
    }
}
