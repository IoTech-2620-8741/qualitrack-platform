package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create raw material resource.
 */
@Schema(
        name = "CreateRawMaterialRequest",
        description = "Request payload for registering a new raw material with initial stock",
        example = "{\"name\": \"Distilled Water\", \"code\": \"RM-DW-001\", \"supplier\": \"AquaChem\", \"batchNumber\": \"B-202606\", \"expirationDate\": \"2027-06-06\", \"quantityInStock\": 500, \"unit\": \"Liters\", \"minimumStock\": 50}"
)
public record CreateRawMaterialResource(

        @Schema(description = "Raw material name", example = "Distilled Water", minLength = 1, maxLength = 150)
        String name,

        @Schema(description = "Internal catalog code", example = "RM-DW-001", minLength = 1, maxLength = 50)
        String code,

        @Schema(description = "External supplier name", example = "AquaChem", minLength = 1, maxLength = 150)
        String supplier,

        @Schema(description = "Batch or lot number", example = "B-202606", minLength = 1, maxLength = 50)
        String batchNumber,

        @Schema(description = "Expiration date in ISO 8601 format", example = "2027-06-06", minLength = 10, maxLength = 20)
        String expirationDate,

        @Schema(description = "Initial stock quantity", example = "500", minimum = "0")
        BigDecimal quantityInStock,

        @Schema(description = "Measurement unit (e.g., Kg, Liters, Units)", example = "Liters", minLength = 1, maxLength = 20)
        String unit,

        @Schema(description = "Minimum stock threshold to trigger alerts", example = "50", minimum = "0")
        BigDecimal minimumStock
) {
    /**
     * Validates the resource properties (Fail-Fast).
     * @throws IllegalArgumentException if required fields are missing or invalid.
     */
    public CreateRawMaterialResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code is required");
        }
        if (supplier == null || supplier.isBlank()) {
            throw new IllegalArgumentException("Supplier is required");
        }
        if (batchNumber == null || batchNumber.isBlank()) {
            throw new IllegalArgumentException("Batch number is required");
        }
        if (expirationDate == null || expirationDate.isBlank()) {
            throw new IllegalArgumentException("Expiration date is required");
        }
        if (quantityInStock == null || quantityInStock.signum() < 0) {
            throw new IllegalArgumentException("Quantity in stock must be provided and cannot be negative");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Unit is required");
        }
        if (minimumStock == null || minimumStock.signum() < 0) {
            throw new IllegalArgumentException("Minimum stock must be provided and cannot be negative");
        }
    }
}
