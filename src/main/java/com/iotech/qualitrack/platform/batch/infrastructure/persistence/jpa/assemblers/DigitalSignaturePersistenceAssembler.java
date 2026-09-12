package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.batch.domain.model.entities.DigitalSignature;
import com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities.DigitalSignaturePersistenceEntity;

/**
 * Static assembler between digital signature domain and persistence representations.
 */
public final class DigitalSignaturePersistenceAssembler {

    private DigitalSignaturePersistenceAssembler() {
    }

    public static DigitalSignature toDomainFromPersistence(DigitalSignaturePersistenceEntity entity) {
        if (entity == null) return null;

        return new DigitalSignature(
                entity.getId(),
                entity.getBatchId(),
                entity.getSignedByUserId(),
                entity.getSignatureHash(),
                entity.getSignedAt()
        );
    }

    public static DigitalSignaturePersistenceEntity toPersistenceFromDomain(DigitalSignature signature) {
        if (signature == null) return null;

        var entity = new DigitalSignaturePersistenceEntity();

        if (signature.getId() != null) {
            entity.setId(signature.getId());
        }

        entity.setBatchId(signature.getBatchId());
        entity.setSignedByUserId(signature.getSignedByUserId());
        entity.setSignatureHash(signature.getSignatureHash());
        entity.setSignedAt(signature.getSignedAt());

        return entity;
    }
}