package com.iotech.qualitrack.platform.iam.application.queryservices;

import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application query service contract for IAM role read operations.
 */
public interface RoleQueryService {

    Optional<Role> handle(GetRoleByNameQuery query);

    List<Role> handle(GetAllRolesQuery query);
}