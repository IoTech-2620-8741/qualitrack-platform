package com.iotech.qualitrack.platform.inventory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.inventory.domain.model.entities.InventoryMovement;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.InventoryMovementResource;

public final class InventoryMovementResourceFromEntityAssembler {
    private InventoryMovementResourceFromEntityAssembler() { }

    public static InventoryMovementResource toResourceFromEntity(InventoryMovement value) {
        return new InventoryMovementResource(value.id(), value.laboratoryId(), value.materialId(), value.receiptId(), value.productBatchId(), value.type(), value.amount(), value.unit(), value.stockBefore(), value.stockAfter(), value.statusBefore(), value.statusAfter(), value.reason(), value.actorId(), value.occurredAt(), value.operationId());
    }
}
