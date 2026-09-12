package com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;
import com.iotech.qualitrack.platform.iam.domain.repositories.RoleRepository;
import com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.assemblers.RolePersistenceAssembler;
import com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.repositories.RolePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for IAM role repository port.
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RolePersistenceRepository rolePersistenceRepository;

    public RoleRepositoryImpl(RolePersistenceRepository rolePersistenceRepository) {
        this.rolePersistenceRepository = rolePersistenceRepository;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return rolePersistenceRepository.findById(id)
                .map(RolePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Role> findByName(Roles name) {
        return rolePersistenceRepository.findByName(name)
                .map(RolePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Role> findAll() {
        return rolePersistenceRepository.findAll().stream()
                .map(RolePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Role save(Role role) {
        var entity = RolePersistenceAssembler.toPersistenceFromDomain(role);
        var savedEntity = rolePersistenceRepository.save(entity);

        return RolePersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public boolean existsByName(Roles name) {
        return rolePersistenceRepository.existsByName(name);
    }
}