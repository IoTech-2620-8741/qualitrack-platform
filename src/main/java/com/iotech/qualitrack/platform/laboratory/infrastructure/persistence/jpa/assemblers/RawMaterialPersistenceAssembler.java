package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities.RawMaterialPersistenceEntity;

/**
 * Static assembler between raw material domain and persistence representations.
 */
public final class RawMaterialPersistenceAssembler {

    private RawMaterialPersistenceAssembler() {
    }

    public static RawMaterial toDomainFromPersistence(RawMaterialPersistenceEntity entity) {
        if (entity == null) return null;

        return new RawMaterial(
                entity.getId(),
                entity.getLaboratoryId(),
                entity.getName(),
                entity.getCode(),
                entity.getSupplier(),
                entity.getBatchNumber(),
                entity.getExpirationDate(),
                entity.getQuantityInStock(),
                entity.getUnit(),
                entity.getMinimumStock()
        );
    }

    public static RawMaterialPersistenceEntity toPersistenceFromDomain(RawMaterial rawMaterial) {
        if (rawMaterial == null) return null;

        var entity = new RawMaterialPersistenceEntity();

        // Only set ID if the raw material is being updated (has a non-null ID)
        // For new raw materials, leave ID null to allow JPA to generate it
        if (rawMaterial.getId() != null) {
            entity.setId(rawMaterial.getId());
        }

        entity.setLaboratoryId(rawMaterial.getLaboratoryId());
        entity.setName(rawMaterial.getName());
        entity.setCode(rawMaterial.getCode());
        entity.setSupplier(rawMaterial.getSupplier());
        entity.setBatchNumber(rawMaterial.getBatchNumber());
        entity.setExpirationDate(rawMaterial.getExpirationDate());

        entity.setQuantityInStock(rawMaterial.getCurrentStock());
        entity.setUnit(rawMaterial.getUnit());
        entity.setMinimumStock(rawMaterial.getMinimumThreshold());

        return entity;
    }
}
