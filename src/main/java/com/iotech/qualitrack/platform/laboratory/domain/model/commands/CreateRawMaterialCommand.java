package com.iotech.qualitrack.platform.laboratory.domain.model.commands;

import java.math.BigDecimal;

/**
 * Command to request the registration of a new Raw Material.
 *
 * @param laboratoryId The numeric identifier of the laboratory. Cannot be null or less than 1.
 * @param name The common or chemical name. Cannot be null or blank.
 * @param code The internal catalog code. Cannot be null or blank.
 * @param supplier The external supplier name. Cannot be null or blank.
 * @param batchNumber The batch or lot number. Cannot be null or blank.
 * @param expirationDate The expiration date (ISO 8601). Cannot be null or blank.
 * @param quantityInStock The initial quantity. Cannot be null or less than 0.
 * @param unit The unit of measurement. Cannot be null or blank.
 * @param minimumStock The minimum threshold for alerts. Cannot be null or less than 0.
 */
public record CreateRawMaterialCommand(
        Long laboratoryId,
        String name,
        String code,
        String supplier,
        String batchNumber,
        String expirationDate,
        BigDecimal quantityInStock,
        String unit,
        BigDecimal minimumStock
) {
    /**
     * Compact constructor for CreateRawMaterialCommand.
     * Enforces Fail-Fast validation.
     */
    public CreateRawMaterialCommand {
        unit = com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit.normalize(unit);
        com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit.validateQuantity(quantityInStock, unit, true);
        com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit.validateQuantity(minimumStock, unit, true);
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code cannot be null or blank");
        }
        if (supplier == null || supplier.isBlank()) {
            throw new IllegalArgumentException("supplier cannot be null or blank");
        }
        if (batchNumber == null || batchNumber.isBlank()) {
            throw new IllegalArgumentException("batchNumber cannot be null or blank");
        }
        if (expirationDate == null || expirationDate.isBlank()) {
            throw new IllegalArgumentException("expirationDate cannot be null or blank");
        }
        if (quantityInStock == null || quantityInStock.signum() < 0) {
            throw new IllegalArgumentException("quantityInStock cannot be null or negative");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit cannot be null or blank");
        }
        if (minimumStock == null || minimumStock.signum() < 0) {
            throw new IllegalArgumentException("minimumStock cannot be null or negative");
        }
    }
}
