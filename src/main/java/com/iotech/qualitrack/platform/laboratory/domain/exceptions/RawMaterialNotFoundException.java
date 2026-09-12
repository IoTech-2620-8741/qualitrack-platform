package com.iotech.qualitrack.platform.laboratory.domain.exceptions;

/**
 * Exception thrown when a raw material is not found.
 */
public class RawMaterialNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param rawMaterialId The numeric ID of the raw material that was not found.
     */
    public RawMaterialNotFoundException(Long rawMaterialId) {
        super(String.format("Raw material with ID %d not found.", rawMaterialId));
    }
}