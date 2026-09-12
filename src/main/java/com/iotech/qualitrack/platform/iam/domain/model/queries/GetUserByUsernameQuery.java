package com.iotech.qualitrack.platform.iam.domain.model.queries;

/**
 * Query used to retrieve an IAM user by username.
 *
 * @param username account username
 */
public record GetUserByUsernameQuery(String username) {
    public GetUserByUsernameQuery {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username cannot be null or blank");
        }
    }
}