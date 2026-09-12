package com.iotech.qualitrack.platform.laboratory.domain.exceptions;

/**
 * Exception thrown when a laboratory is not found.
 */
public class LaboratoryNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param laboratoryId The numeric ID of the laboratory that was not found.
     */
    public LaboratoryNotFoundException(Long laboratoryId) {
        super(String.format("Laboratory with ID %d not found.", laboratoryId));
    }
}