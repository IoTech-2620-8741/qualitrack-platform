package com.iotech.qualitrack.platform.iam.application.internal.queryservices;

import com.iotech.qualitrack.platform.iam.application.queryservices.RoleQueryService;
import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetRoleByNameQuery;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;
import com.iotech.qualitrack.platform.iam.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application query service implementation for IAM roles.
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> handle(GetRoleByNameQuery query) {
        return roleRepository.findByName(Roles.valueOf(query.roleName()));
    }

    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }
}