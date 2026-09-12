package com.iotech.qualitrack.platform.laboratory.interfaces.events;

import java.math.BigDecimal;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialLowStockEvent;

/**
 * Integration event published by the Laboratory bounded context when a raw material stock
 * falls below its safety threshold.
 *
 * <p>This event allows Compliance and Alerts, Batch, or Reporting contexts to react
 * without depending on Laboratory internal domain events.</p>
 *
 * @param rawMaterialId the raw material identifier
 * @param laboratoryId the laboratory that owns the material
 * @param materialName the raw material display name
 * @param currentStock the current stock quantity
 * @param minimumThreshold the minimum safety threshold
 */
public record RawMaterialLowStockIntegrationEvent(
        Long rawMaterialId,
        Long laboratoryId,
        String materialName,
        BigDecimal currentStock,
        BigDecimal minimumThreshold
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal raw material low stock domain event
     * @return the integration event
     */
    public static RawMaterialLowStockIntegrationEvent from(RawMaterialLowStockEvent event) {
        return new RawMaterialLowStockIntegrationEvent(
                event.rawMaterialId(),
                event.laboratoryId(),
                event.materialName(),
                event.currentStock(),
                event.minimumThreshold()
        );
    }
}
