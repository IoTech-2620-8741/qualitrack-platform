package com.iotech.qualitrack.platform.iam.domain.model.commands;

/**
 * Command used to assign an existing role to an existing user.
 *
 * @param userId target user identifier
 * @param roleName role name to assign
 */
public record AssignRoleCommand(
        Long userId,
        String roleName
) {
    public AssignRoleCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }

        if (roleName == null || roleName.isBlank()) {
            throw new IllegalArgumentException("roleName cannot be null or blank");
        }
    }
}