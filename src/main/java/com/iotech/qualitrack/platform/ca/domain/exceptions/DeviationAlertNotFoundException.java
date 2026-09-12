package com.iotech.qualitrack.platform.ca.domain.exceptions;

/**
 * Exception thrown when a deviation alert is not found.
 */
public class DeviationAlertNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param alertId The numeric ID of the deviation alert that was not found.
     */
    public DeviationAlertNotFoundException(Long alertId) {
        super(String.format("Deviation alert with ID %d not found.", alertId));
    }
}