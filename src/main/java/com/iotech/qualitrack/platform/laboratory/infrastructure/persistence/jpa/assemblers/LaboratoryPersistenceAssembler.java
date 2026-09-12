package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.domain.model.entities.LaboratoryAddress;
import com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects.LaboratoryName;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities.LaboratoryPersistenceEntity;

import java.util.ArrayList;

/**
 * Assembler class to map between the Laboratory domain aggregate and its JPA persistence entity.
 *
 * <p>Ensures that the core domain remains completely decoupled from the database
 * framework while transferring the state accurately.</p>
 */
public final class LaboratoryPersistenceAssembler {

    private LaboratoryPersistenceAssembler() {
    }

    /**
     * Reconstructs a Laboratory domain entity from a JPA persistence entity.
     *
     * @param entity the JPA entity retrieved from the database
     * @return the reconstructed domain aggregate root
     */
    public static Laboratory toDomainFromPersistence(LaboratoryPersistenceEntity entity) {
        if (entity == null) return null;

        return new Laboratory(
                entity.getId(),
                new LaboratoryName(entity.getName()),
                entity.getRuc(),
                entity.getPhone(),
                new ArrayList<>(entity.getApplicableRegulations()),
                entity.getStatus(),
                new LaboratoryAddress(entity.getAddress())
        );
    }

    /**
     * Maps a Laboratory domain entity to a JPA persistence entity for database storage.
     *
     * @param domain the domain aggregate root
     * @return the JPA entity ready to be persisted
     */
    public static LaboratoryPersistenceEntity toPersistenceFromDomain(Laboratory domain) {
        if (domain == null) return null;

        var entity = new LaboratoryPersistenceEntity();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setName(domain.getName().name());
        entity.setRuc(domain.getRuc());
        entity.setPhone(domain.getPhone());

        if (domain.getApplicableRegulations() != null) {
            entity.setApplicableRegulations(new ArrayList<>(domain.getApplicableRegulations()));
        }

        entity.setStatus(domain.getStatus());
        entity.setAddress(domain.getAddress().getAddress());

        return entity;
    }
}