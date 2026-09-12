package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing the digital signature generated when
 * a production batch is released.
 */
@Entity
@Table(name = "digital_signatures")
@Getter
@Setter
@NoArgsConstructor
public class DigitalSignaturePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    @Column(name = "signed_by_user_id", nullable = false)
    private Long signedByUserId;

    @Column(name = "signature_hash", nullable = false, length = 255)
    private String signatureHash;

    @Column(name = "signed_at", nullable = false, length = 30)
    private String signedAt;
}