package com.iotech.qualitrack.platform.laboratory.domain.model.events;

import java.math.BigDecimal;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;

/**
 * Domain event published when a raw material is successfully created.
 *
 * <p>Other bounded contexts can react to raw material catalog changes without
 * directly depending on laboratory application services.</p>
 *
 * @param rawMaterialId The numeric identity assigned to the raw material.
 * @param laboratoryId The numeric identity of the laboratory that owns the raw material.
 * @param name The raw material name.
 * @param code The internal raw material catalog code.
 * @param currentStock The current stock quantity.
 * @param unit The stock measurement unit.
 * @param minimumThreshold The minimum stock threshold.
 */
public record RawMaterialCreatedEvent(
        Long rawMaterialId,
        Long laboratoryId,
        String name,
        String code,
        BigDecimal currentStock,
        String unit,
        BigDecimal minimumThreshold
) {

    /**
     * Convenience factory that extracts all needed fields from a saved raw material.
     *
     * @param rawMaterial The saved raw material.
     * @return A fully populated {@link RawMaterialCreatedEvent}.
     */
    public static RawMaterialCreatedEvent from(RawMaterial rawMaterial) {
        return new RawMaterialCreatedEvent(
                rawMaterial.getId(),
                rawMaterial.getLaboratoryId(),
                rawMaterial.getName(),
                rawMaterial.getCode(),
                rawMaterial.getCurrentStock(),
                rawMaterial.getUnit(),
                rawMaterial.getMinimumThreshold()
        );
    }
}
