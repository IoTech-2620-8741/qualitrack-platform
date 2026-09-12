package com.iotech.qualitrack.platform.iam.domain.model.commands;

/**
 * Command used to deactivate an IAM user.
 *
 * @param userId target user identifier
 */
public record DeactivateUserCommand(
        Long userId
) {
    public DeactivateUserCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
    }
}