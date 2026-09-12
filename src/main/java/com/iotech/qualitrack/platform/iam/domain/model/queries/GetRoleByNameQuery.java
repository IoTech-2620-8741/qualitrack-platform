package com.iotech.qualitrack.platform.iam.domain.model.queries;

/**
 * Query used to retrieve an IAM role by name.
 *
 * @param roleName role name
 */
public record GetRoleByNameQuery(String roleName) {
    public GetRoleByNameQuery {
        if (roleName == null || roleName.isBlank()) {
            throw new IllegalArgumentException("roleName cannot be null or blank");
        }
    }
}