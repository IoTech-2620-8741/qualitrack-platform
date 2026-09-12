package com.iotech.qualitrack.platform.batch.interfaces.events;

import com.iotech.qualitrack.platform.batch.domain.model.events.RawMaterialLinkedToBatchEvent;

/**
 * Integration event published by the Batch bounded context when raw material usage
 * is linked to a production batch.
 *
 * <p>This event supports traceability, reporting, inventory, and compliance workflows
 * outside the Batch bounded context.</p>
 *
 * @param usageId the raw material usage identifier
 * @param batchId the production batch identifier
 * @param rawMaterialId the consumed raw material identifier
 * @param rawMaterialName the consumed raw material display name
 * @param quantityUsed the consumed quantity
 * @param unit the measurement unit
 */
public record RawMaterialLinkedToBatchIntegrationEvent(
        Long usageId,
        Long batchId,
        Long rawMaterialId,
        String rawMaterialName,
        Double quantityUsed,
        String unit
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal raw material linked domain event
     * @return the integration event
     */
    public static RawMaterialLinkedToBatchIntegrationEvent from(RawMaterialLinkedToBatchEvent event) {
        return new RawMaterialLinkedToBatchIntegrationEvent(
                event.usageId(),
                event.batchId(),
                event.rawMaterialId(),
                event.rawMaterialName(),
                event.quantityUsed(),
                event.unit()
        );
    }
}