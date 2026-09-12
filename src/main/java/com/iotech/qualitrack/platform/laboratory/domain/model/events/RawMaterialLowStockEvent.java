package com.iotech.qualitrack.platform.laboratory.domain.model.events;

import java.math.BigDecimal;

/**
 * Domain event published when a raw material's stock falls below its minimum safety threshold.
 *
 * <p>This event is critical for triggering automated alerts in the Compliance (CA)
 * or Batch management contexts to prevent production delays.</p>
 *
 * @param rawMaterialId    The numeric identity of the raw material.
 * @param laboratoryId     The numeric identity of the laboratory owning the material.
 * @param materialName     The name of the material.
 * @param currentStock     The exact quantity left in stock.
 * @param minimumThreshold The minimum safety threshold that was breached.
 */
public record RawMaterialLowStockEvent(
        Long rawMaterialId,
        Long laboratoryId,
        String materialName,
        BigDecimal currentStock,
        BigDecimal minimumThreshold) {
}
