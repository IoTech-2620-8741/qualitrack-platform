package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing the rejection record generated when
 * a production batch is rejected.
 */
@Entity
@Table(name = "rejection_records")
@Getter
@Setter
@NoArgsConstructor
public class RejectionRecordPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    @Column(name = "rejection_date", nullable = false, length = 30)
    private String rejectionDate;

    @Column(nullable = false, length = 500)
    private String reason;
}