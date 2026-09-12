package com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.entities.RolePersistenceEntity;

/**
 * Assembler that maps IAM role domain entities and persistence entities.
 */
public class RolePersistenceAssembler {

    public static Role toDomainFromPersistence(RolePersistenceEntity entity) {
        if (entity == null) return null;

        return new Role(
                entity.getId(),
                entity.getName()
        );
    }

    public static RolePersistenceEntity toPersistenceFromDomain(Role role) {
        if (role == null) return null;

        var entity = new RolePersistenceEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());

        return entity;
    }
}