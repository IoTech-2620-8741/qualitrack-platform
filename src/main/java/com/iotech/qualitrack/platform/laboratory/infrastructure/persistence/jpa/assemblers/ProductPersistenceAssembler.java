package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.PharmaceuticalProduct;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities.ProductPersistenceEntity;

/**
 * Static assembler between pharmaceutical product domain and persistence representations.
 */
public final class ProductPersistenceAssembler {

    private ProductPersistenceAssembler() {
    }

    public static PharmaceuticalProduct toDomainFromPersistence(ProductPersistenceEntity entity) {
        if (entity == null) return null;

        return new PharmaceuticalProduct(
                entity.getId(),
                entity.getLaboratoryId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getSpecifications(),
                entity.isActive()
        );
    }

    public static ProductPersistenceEntity toPersistenceFromDomain(PharmaceuticalProduct product) {
        if (product == null) return null;

        var entity = new ProductPersistenceEntity();

        // Only set ID if the product is being updated (has a non-null ID)
        // For new products, leave ID null to allow JPA to generate it
        if (product.getId() != null) {
            entity.setId(product.getId());
        }

        entity.setLaboratoryId(product.getLaboratoryId());
        entity.setCode(product.getCode());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setSpecifications(product.getSpecifications());
        entity.setActive(product.isActive());

        return entity;
    }
}