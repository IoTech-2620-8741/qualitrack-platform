package com.iotech.qualitrack.platform.ca.domain.exceptions;

/**
 * Exception thrown when a compliance event is not found.
 */
public class ComplianceEventNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param eventId The numeric ID of the compliance event that was not found.
     */
    public ComplianceEventNotFoundException(Long eventId) {
        super(String.format("Compliance event with ID %d not found.", eventId));
    }
}