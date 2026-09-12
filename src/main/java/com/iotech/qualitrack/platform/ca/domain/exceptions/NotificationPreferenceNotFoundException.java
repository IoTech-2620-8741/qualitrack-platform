package com.iotech.qualitrack.platform.ca.domain.exceptions;

/**
 * Exception thrown when notification preferences are not found for a user.
 */
public class NotificationPreferenceNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param userId The numeric ID of the user whose notification preferences were not found.
     */
    public NotificationPreferenceNotFoundException(Long userId) {
        super(String.format("Notification preferences for user with ID %d not found.", userId));
    }
}