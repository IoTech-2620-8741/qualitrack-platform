package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities.ProductPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link ProductPersistenceEntity}.
 *
 * <p>Handles database operations for the pharmaceutical products table using Long as the primary key.
 * Provides custom queries for fetching products by laboratory and validating unique business rules.</p>
 */
@Repository
public interface ProductPersistenceRepository extends JpaRepository<ProductPersistenceEntity, Long> {

    /**
     * Finds all products associated with a specific laboratory.
     * @param laboratoryId The numeric ID of the laboratory.
     */
    List<ProductPersistenceEntity> findAllByLaboratoryId(Long laboratoryId);

    /**
     * Finds a specific product by its name within a specific laboratory.
     * @param name The name of the product.
     * @param laboratoryId The numeric ID of the laboratory.
     */
    Optional<ProductPersistenceEntity> findByNameAndLaboratoryId(String name, Long laboratoryId);

    /**
     * Finds a product by its unique internal catalog code.
     * @param code The unique internal code.
     */
    Optional<ProductPersistenceEntity> findByCode(String code);

    /**
     * Checks if a product with the given internal catalog code already exists.
     * Useful for validation during product creation.
     */
    boolean existsByCode(String code);
}