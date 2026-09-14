package com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.assemblers;
import com.iotech.qualitrack.platform.inventory.domain.model.entities.InventoryMovement;
import com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.entities.InventoryMovementEntity;

public final class InventoryMovementPersistenceAssembler {
    private InventoryMovementPersistenceAssembler() { }
    public static InventoryMovement toDomainFromPersistence(InventoryMovementEntity entity) {
        return new InventoryMovement(entity.getId(), entity.getLaboratoryId(), entity.getMaterialId(), entity.getReceiptId(),
            entity.getProductBatchId(), entity.getType(), entity.getAmount(), entity.getUnit(), entity.getStockBefore(),
            entity.getStockAfter(), entity.getStatusBefore(), entity.getStatusAfter(), entity.getReason(),
            entity.getActorId(), entity.getOccurredAt(), entity.getOperationId());
    }
    public static InventoryMovementEntity toPersistenceFromDomain(InventoryMovement movement) {
        var entity = new InventoryMovementEntity();
        entity.setLaboratoryId(movement.laboratoryId());
        entity.setMaterialId(movement.materialId());
        entity.setReceiptId(movement.receiptId());
        entity.setProductBatchId(movement.productBatchId());
        entity.setType(movement.type());
        entity.setAmount(movement.amount());
        entity.setUnit(movement.unit());
        entity.setStockBefore(movement.stockBefore());
        entity.setStockAfter(movement.stockAfter());
        entity.setStatusBefore(movement.statusBefore());
        entity.setStatusAfter(movement.statusAfter());
        entity.setReason(movement.reason());
        entity.setActorId(movement.actorId());
        entity.setOccurredAt(movement.occurredAt());
        entity.setOperationId(movement.operationId());
        return entity;
    }
}
