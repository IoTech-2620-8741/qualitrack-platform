package com.iotech.qualitrack.platform.iam.domain.model.commands;

/**
 * Command used to authenticate an existing IAM user.
 *
 * @param username account username
 * @param password raw account password
 */
public record SignInCommand(
        String username,
        String password
) {
    public SignInCommand {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username cannot be null or blank");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password cannot be null or blank");
        }
    }
}