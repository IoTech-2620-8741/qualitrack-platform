package com.iotech.qualitrack.platform.iam.domain.repositories;

import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;

import java.util.List;
import java.util.Optional;

/**
 * Role repository port.
 *
 * <p>Defines persistence operations for IAM authorization roles.</p>
 */
public interface RoleRepository {

    Optional<Role> findById(Long id);

    Optional<Role> findByName(Roles name);

    List<Role> findAll();

    Role save(Role role);

    boolean existsByName(Roles name);
}