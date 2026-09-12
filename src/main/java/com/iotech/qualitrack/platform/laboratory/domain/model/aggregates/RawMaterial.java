package com.iotech.qualitrack.platform.laboratory.domain.model.aggregates;

import java.math.BigDecimal;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateRawMaterialCommand;
import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * Raw Material aggregate root.
 *
 * <p>Represents a raw material used by a laboratory within the QualiTrack platform.
 * Governs the state of the material, its stock levels, and threshold alerts.</p>
 */
@Getter
public class RawMaterial extends AbstractDomainAggregateRoot<RawMaterial> {

    /**
     * The unique numeric identifier for the raw material.
     */
    private Long id;

    /**
     * The numeric identifier of the laboratory that owns this material.
     */
    private Long laboratoryId;

    private String name;
    private String code;
    private String supplier;
    private String batchNumber;
    private String expirationDate;
    private BigDecimal currentStock;
    private String unit;
    private BigDecimal minimumThreshold;

    /**
     * Default constructor.
     * Required for reconstruction by JPA or Assemblers.
     */
    public RawMaterial() {
        // Required for reconstruction
    }

    /**
     * Constructor for reconstruction from the persistence layer (Assembler).
     *
     * @param id The numeric ID.
     * @param laboratoryId The laboratory ID.
     * @param name The material name.
     * @param code The internal catalog code.
     * @param supplier The external supplier.
     * @param batchNumber The batch number.
     * @param expirationDate The expiration date.
     * @param currentStock The current stock level.
     * @param unit The unit of measurement.
     * @param minimumThreshold The minimum stock threshold.
     */
    public RawMaterial(Long id, Long laboratoryId, String name, String code, String supplier,
                       String batchNumber, String expirationDate, BigDecimal currentStock,
                       String unit, BigDecimal minimumThreshold) {
        this.id = id;
        this.laboratoryId = laboratoryId;
        this.name = name;
        this.code = code;
        this.supplier = supplier;
        this.batchNumber = batchNumber;
        this.expirationDate = expirationDate;
        this.currentStock = currentStock;
        this.unit = unit;
        this.minimumThreshold = minimumThreshold;
    }

    /**
     * Constructor for creating a new RawMaterial from a command.
     *
     * @param command The CreateRawMaterialCommand containing initialization data.
     */
    public RawMaterial(CreateRawMaterialCommand command) {
        this.laboratoryId = Objects.requireNonNull(command.laboratoryId(), "Laboratory ID is required");
        this.name = Objects.requireNonNull(command.name(), "Material name is required");
        this.code = Objects.requireNonNull(command.code(), "Code is required");
        this.supplier = Objects.requireNonNull(command.supplier(), "Supplier is required");
        this.batchNumber = Objects.requireNonNull(command.batchNumber(), "Batch number is required");
        this.expirationDate = Objects.requireNonNull(command.expirationDate(), "Expiration date is required");
        this.currentStock = Objects.requireNonNull(command.quantityInStock(), "Initial stock is required");
        this.unit = Objects.requireNonNull(command.unit(), "Unit is required");
        this.minimumThreshold = Objects.requireNonNull(command.minimumStock(), "Minimum stock is required");
    }

    public void consumeStock(BigDecimal amount, String requestedUnit) {
        com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit.requireSame(unit, requestedUnit);
        com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit.validateQuantity(amount, unit, false);
        if (amount.compareTo(currentStock) > 0) {
            throw new IllegalStateException("Insufficient stock: available " + currentStock.toPlainString()
                    + " " + unit + ", requested " + amount.toPlainString() + " " + unit);
        }
        currentStock = currentStock.subtract(amount);
    }
}
