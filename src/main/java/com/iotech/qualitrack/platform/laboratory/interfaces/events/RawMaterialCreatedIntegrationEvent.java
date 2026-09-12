package com.iotech.qualitrack.platform.laboratory.interfaces.events;

import java.math.BigDecimal;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialCreatedEvent;

/**
 * Integration event published when a raw material is created.
 *
 * <p>This is part of the published language of the Laboratory bounded context.</p>
 *
 * @param rawMaterialId The created raw material identifier.
 * @param laboratoryId The laboratory identifier that owns the raw material.
 * @param name The raw material name.
 * @param code The internal raw material catalog code.
 * @param currentStock The current stock quantity.
 * @param unit The stock measurement unit.
 * @param minimumThreshold The minimum stock threshold.
 */
public record RawMaterialCreatedIntegrationEvent(
        Long rawMaterialId,
        Long laboratoryId,
        String name,
        String code,
        BigDecimal currentStock,
        String unit,
        BigDecimal minimumThreshold
) {

    /**
     * Creates an integration event from the internal Laboratory domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static RawMaterialCreatedIntegrationEvent from(RawMaterialCreatedEvent event) {
        return new RawMaterialCreatedIntegrationEvent(
                event.rawMaterialId(),
                event.laboratoryId(),
                event.name(),
                event.code(),
                event.currentStock(),
                event.unit(),
                event.minimumThreshold()
        );
    }
}
