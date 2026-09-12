package com.iotech.qualitrack.platform.ca.domain.model.queries;

/**
 * Query to get notification preferences by user id.
 *
 * @param userId The ID of the user. Cannot be null or less than 1.
 */
public record GetNotificationPreferenceByUserIdQuery(Long userId) {
    /**
     * Compact constructor for GetNotificationPreferenceByUserIdQuery.
     * Validates that the userId is not null and is greater than 0.
     *
     * @throws IllegalArgumentException if userId is null or less than 1.
     */
    public GetNotificationPreferenceByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id is required and must be greater than 0.");
        }
    }
}