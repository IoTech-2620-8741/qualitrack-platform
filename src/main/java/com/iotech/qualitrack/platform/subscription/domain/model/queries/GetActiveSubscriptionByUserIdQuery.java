package com.iotech.qualitrack.platform.subscription.domain.model.queries;

/**
 * Query used to retrieve the active subscription for a user.
 *
 * @param userId The user identifier.
 */
public record GetActiveSubscriptionByUserIdQuery(
        Long userId
) {

    public GetActiveSubscriptionByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be a positive number");
        }
    }
}