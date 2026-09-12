package com.iotech.qualitrack.platform.iam.domain.model.queries;

/**
 * Query used to retrieve an IAM user by identifier.
 *
 * @param userId user identifier
 */
public record GetUserByIdQuery(Long userId) {
    public GetUserByIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
    }
}