package com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.assemblers;
import com.iotech.qualitrack.platform.inventory.domain.model.aggregates.RawMaterialBatch;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.ContainerAssignment;
import com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.entities.InventoryReceiptPersistenceEntity;

/**
 * Maps supplier raw material batches between domain and persistence.
 *
 * <p>Preserves received and available quantities, supplier details,
 * quality review status and optional monitored container assignment.</p>
 */
public final class InventoryReceiptPersistenceAssembler {
    private InventoryReceiptPersistenceAssembler() { }

    /**
     * Reconstructs a raw material batch from its persistence entity.
     *
     * <p>Restores the monitored container assignment when present.</p>
     *
     * @param entity persisted supplier batch
     * @return reconstructed raw material batch
     */
    public static RawMaterialBatch toDomainFromPersistence(InventoryReceiptPersistenceEntity entity) {
        return new RawMaterialBatch(entity.getId(), entity.getLaboratoryId(), entity.getMaterialId(), entity.getSupplier(),
            entity.getBatchNumber(), entity.getUnit(), entity.getInitialAmount(), entity.getAvailableAmount(),
            entity.getReceivedOn(), entity.getExpiresOn(), entity.getStatus(),
            entity.getContainerMonitorId() == null ? null : new ContainerAssignment(entity.getContainerMonitorId(),
                entity.getContainerEnvironmentId(), entity.getContainerAssignedBy(), entity.getContainerAssignedAt()));
    }

    /**
     * Converts a raw material batch into a persistence entity.
     *
     * <p>Includes the container monitor assignment when available.</p>
     *
     * @param receipt raw material batch
     * @return persistence entity representing the batch
     */
    public static InventoryReceiptPersistenceEntity toPersistenceFromDomain(RawMaterialBatch receipt) {
        var entity = new InventoryReceiptPersistenceEntity();
        entity.setId(receipt.getId());
        entity.setLaboratoryId(receipt.getLaboratoryId());
        entity.setMaterialId(receipt.getRawMaterialId());
        entity.setSupplier(receipt.getSupplier());
        entity.setBatchNumber(receipt.getBatchNumber());
        entity.setUnit(receipt.getUnit());
        entity.setInitialAmount(receipt.getInitialAmount());
        entity.setAvailableAmount(receipt.getAvailableAmount());
        entity.setReceivedOn(receipt.getReceivedOn());
        entity.setExpiresOn(receipt.getExpiresOn());
        entity.setStatus(receipt.getStatus());
        receipt.container().ifPresent(container -> {
            entity.setContainerMonitorId(container.containerMonitorId());
            entity.setContainerEnvironmentId(container.environmentId());
            entity.setContainerAssignedBy(container.assignedBy());
            entity.setContainerAssignedAt(container.assignedAt());
        });
        return entity;
    }
}
