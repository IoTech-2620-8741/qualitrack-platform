package com.iotech.qualitrack.platform.iam.application.internal.commandservices;

import com.iotech.qualitrack.platform.iam.application.commandservices.RoleCommandService;
import com.iotech.qualitrack.platform.iam.domain.model.commands.SeedRolesCommand;
import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;
import com.iotech.qualitrack.platform.iam.domain.repositories.RoleRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application command service implementation for IAM roles.
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Result<Integer, ApplicationError> handle(SeedRolesCommand command) {
        try {
            int createdRoles = 0;

            for (var roleName : Roles.values()) {
                if (!roleRepository.existsByName(roleName)) {
                    roleRepository.save(new Role(roleName));
                    createdRoles++;
                }
            }

            return Result.success(createdRoles);

        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("seed-roles", e.getMessage()));
        }
    }
}