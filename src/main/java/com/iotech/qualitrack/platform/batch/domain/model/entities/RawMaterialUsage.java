package com.iotech.qualitrack.platform.batch.domain.model.entities;

import com.iotech.qualitrack.platform.batch.domain.model.commands.LinkRawMaterialCommand;
import lombok.Getter;

import java.util.Objects;

/**
 * The RawMaterialUsage domain entity.
 *
 * <p>Represents the consumption of a raw material within a production batch.
 * It provides traceability between the batch and the raw material used during
 * the manufacturing process.</p>
 */
@Getter
public class RawMaterialUsage {

    /**
     * The unique internal numeric identifier for the usage record.
     */
    private Long id;
    private java.math.BigDecimal stockBefore;
    private java.math.BigDecimal stockAfter;

    public void recordStockChange(java.math.BigDecimal before, java.math.BigDecimal after) {
        this.stockBefore = before;
        this.stockAfter = after;
    }

    /**
     * The numeric identifier of the production batch.
     */
    private Long batchId;

    /**
     * The numeric identifier of the consumed raw material.
     */
    private Long rawMaterialId;

    /**
     * The display name of the consumed raw material.
     */
    private String rawMaterialName;

    /**
     * The quantity of raw material consumed.
     */
    private Double quantityUsed;

    /**
     * The unit of measurement for the consumed quantity.
     */
    private String unit;

    /**
     * The date when the raw material was used.
     */
    private String usageDate;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public RawMaterialUsage() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a RawMaterialUsage entity from persistence data.
     *
     * @param id The unique numeric ID.
     * @param batchId The batch ID.
     * @param rawMaterialId The raw material ID.
     * @param rawMaterialName The raw material display name.
     * @param quantityUsed The consumed quantity.
     * @param unit The measurement unit.
     * @param usageDate The usage date.
     */
    public RawMaterialUsage(Long id, Long batchId, Long rawMaterialId, String rawMaterialName,
                            Double quantityUsed, String unit, String usageDate) {
        this.id = id;
        this.batchId = batchId;
        this.rawMaterialId = rawMaterialId;
        this.rawMaterialName = rawMaterialName;
        this.quantityUsed = quantityUsed;
        this.unit = unit;
        this.usageDate = usageDate;
    }

    /**
     * Constructs a new RawMaterialUsage from a command.
     *
     * @param command The command containing the raw material usage data.
     * @param rawMaterialName The raw material display name.
     * @param unit The measurement unit.
     * @param usageDate The date when the material was used.
     */
    public RawMaterialUsage(LinkRawMaterialCommand command, String rawMaterialName, String unit, String usageDate) {
        this.batchId = Objects.requireNonNull(command.batchId(), "Batch ID is required");
        this.rawMaterialId = Objects.requireNonNull(command.rawMaterialId(), "Raw material ID is required");
        this.rawMaterialName = Objects.requireNonNull(rawMaterialName, "Raw material name is required");
        this.quantityUsed = Objects.requireNonNull(command.quantityUsed(), "Quantity used is required");
        this.unit = Objects.requireNonNull(unit, "Unit is required");

        if (usageDate == null || usageDate.isBlank()) {
            throw new IllegalArgumentException("Usage date cannot be null or blank");
        }
        this.usageDate = usageDate;
    }
}
