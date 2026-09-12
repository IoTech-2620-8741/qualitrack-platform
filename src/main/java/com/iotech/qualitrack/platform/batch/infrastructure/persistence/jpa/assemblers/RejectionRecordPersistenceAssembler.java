package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.batch.domain.model.entities.RejectionRecord;
import com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities.RejectionRecordPersistenceEntity;

/**
 * Static assembler between rejection record domain and persistence representations.
 */
public final class RejectionRecordPersistenceAssembler {

    private RejectionRecordPersistenceAssembler() {
    }

    public static RejectionRecord toDomainFromPersistence(RejectionRecordPersistenceEntity entity) {
        if (entity == null) return null;

        return new RejectionRecord(
                entity.getId(),
                entity.getBatchId(),
                entity.getRejectionDate(),
                entity.getReason()
        );
    }

    public static RejectionRecordPersistenceEntity toPersistenceFromDomain(RejectionRecord record) {
        if (record == null) return null;

        var entity = new RejectionRecordPersistenceEntity();

        if (record.getId() != null) {
            entity.setId(record.getId());
        }

        entity.setBatchId(record.getBatchId());
        entity.setRejectionDate(record.getRejectionDate());
        entity.setReason(record.getReason());

        return entity;
    }
}