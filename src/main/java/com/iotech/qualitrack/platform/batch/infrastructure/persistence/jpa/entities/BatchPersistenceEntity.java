package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a production Batch table.
 *
 * <p>This class bridges the relational database schema with the application's
 * data access layer. It reflects the structure required by the Angular frontend
 * for batch lifecycle and traceability operations.</p>
 */
@Entity
@Table(name = "batches")
@Getter
@Setter
@NoArgsConstructor
public class BatchPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "laboratory_id", nullable = false)
    private Long labId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "batch_number", nullable = false, length = 50, unique = true)
    private String batchNumber;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false, length = 20)
    private String unit;

    /**
     * Converted automatically by BatchStatusPersistenceConverter.
     */
    @Column(nullable = false, length = 50)
    private BatchStatus status;

    @Column(name = "start_date", nullable = false, length = 30)
    private String startDate;

    @Column(name = "end_date", length = 30)
    private String endDate;

    @Column(length = 500)
    private String notes;
}