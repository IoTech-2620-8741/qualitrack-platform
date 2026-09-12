package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities;

import java.math.BigDecimal;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a Raw Material table.
 *
 * <p>This class bridges the relational database schema with the application's
 * data access layer. It reflects the exact structure defined by the Angular frontend,
 * including tracking codes, supplier details, batch numbers, and stock levels.</p>
 */
@Entity
@Table(name = "raw_materials")
@Getter
@Setter
@NoArgsConstructor
public class RawMaterialPersistenceEntity extends AuditableAbstractPersistenceEntity {

    // The numeric ID (Long) is inherited from AuditableAbstractPersistenceEntity.
    // The previous String 'domainId' has been removed to unify IDs across layers.

    @Column(name = "laboratory_id", nullable = false)
    private Long laboratoryId;

    @Column(nullable = false, length = 150)
    private String name;

    /**
     * The internal catalog code that uniquely identifies this raw material.
     */
    @Column(nullable = false, length = 50, unique = true)
    private String code;

    /**
     * The name of the external supplier or vendor.
     */
    @Column(nullable = false, length = 150)
    private String supplier;

    /**
     * The batch or lot number assigned by the supplier.
     */
    @Column(name = "batch_number", nullable = false, length = 50)
    private String batchNumber;

    /**
     * The expiration date of this raw material batch (stored as ISO 8601 String).
     */
    @Column(name = "expiration_date", nullable = false, length = 20)
    private String expirationDate;

    /**
     * The current quantity available in stock.
     */
    @Column(name = "quantity_in_stock", nullable = false, precision = 19, scale = 3)
    private BigDecimal quantityInStock;

    @Column(nullable = false, length = 20)
    private String unit;

    /**
     * The minimum stock threshold below which a restocking action is triggered.
     */
    @Column(name = "minimum_stock", nullable = false, precision = 19, scale = 3)
    private BigDecimal minimumStock;
}
