package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a Pharmaceutical Product table.
 *
 * <p>This class bridges the relational database schema with the application's
 * data access layer. It reflects the exact structure defined by the Angular frontend,
 * including internal catalog codes, technical specifications, and active status.</p>
 */
@Entity
@Table(name = "pharmaceutical_products")
@Getter
@Setter
@NoArgsConstructor
public class ProductPersistenceEntity extends AuditableAbstractPersistenceEntity {

    // The numeric ID (Long) is inherited from AuditableAbstractPersistenceEntity.
    // The previous String 'domainId' has been removed to unify IDs across layers.

    @Column(name = "laboratory_id", nullable = false)
    private Long laboratoryId;

    /**
     * The internal registration code that uniquely identifies this product
     * within the laboratory's catalog.
     */
    @Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    /**
     * The technical specifications of the pharmaceutical product.
     * Replaces the old 'activeIngredient' to match frontend requirements.
     */
    @Column(nullable = false, length = 1000)
    private String specifications;

    /**
     * Indicates whether this pharmaceutical product is currently active in the system.
     * Defaults to true upon creation.
     */
    @Column(nullable = false)
    private boolean active = true;
}