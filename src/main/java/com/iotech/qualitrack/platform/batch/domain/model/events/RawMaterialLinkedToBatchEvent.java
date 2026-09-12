package com.iotech.qualitrack.platform.batch.domain.model.events;

import com.iotech.qualitrack.platform.batch.domain.model.entities.RawMaterialUsage;

/**
 * Domain event published when a raw material usage record is linked to a production batch.
 *
 * <p>Can be listened to by inventory, compliance, or reporting contexts to update
 * stock traceability and production genealogy.</p>
 *
 * @param usageId         The numeric identity of the raw material usage record.
 * @param batchId         The numeric identity of the production batch.
 * @param rawMaterialId   The numeric identity of the consumed raw material.
 * @param rawMaterialName The display name of the consumed raw material.
 * @param quantityUsed    The quantity consumed by the batch.
 * @param unit            The measurement unit.
 */
public record RawMaterialLinkedToBatchEvent(
        Long usageId,
        Long batchId,
        Long rawMaterialId,
        String rawMaterialName,
        Double quantityUsed,
        String unit) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link RawMaterialUsage}.
     *
     * @param usage the saved raw material usage record
     * @return a fully populated {@link RawMaterialLinkedToBatchEvent}
     */
    public static RawMaterialLinkedToBatchEvent from(RawMaterialUsage usage) {
        return new RawMaterialLinkedToBatchEvent(
                usage.getId(),
                usage.getBatchId(),
                usage.getRawMaterialId(),
                usage.getRawMaterialName(),
                usage.getQuantityUsed(),
                usage.getUnit()
        );
    }
}